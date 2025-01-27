package it.disim.univaq.sose.search_service.business.impl;

import it.disim.univaq.sose.ads_service.webservice.*;
import it.disim.univaq.sose.geolocation_service.webservice.CalculateDistance;
import it.disim.univaq.sose.geolocation_service.webservice.GeolocationService;
import it.disim.univaq.sose.geolocation_service.webservice.GetDistanceResponse;
import it.disim.univaq.sose.geolocation_service.webservice.GetLocationDetails;
import it.disim.univaq.sose.geolocation_service.webservice.GetLocationDetailsResponse;
import it.disim.univaq.sose.search_service.business.SearchManager;
import it.disim.univaq.sose.search_service.client.AdsServiceClient;
import it.disim.univaq.sose.search_service.client.GeolocationServiceClient;
import it.disim.univaq.sose.search_service.domain.dto.ErrorResponse;
import it.disim.univaq.sose.search_service.domain.dto.SearchByCityRequest;
import it.disim.univaq.sose.search_service.domain.dto.SearchByLatLongRequest;
import it.disim.univaq.sose.search_service.domain.dto.SearchByTitleRequest;
import it.disim.univaq.sose.search_service.service.ServiceUnavailableException;

import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SearchManagerImpl implements SearchManager {
    private final AdsServiceClient adsServiceClient;

    private final GeolocationServiceClient geolocationServiceClient;

    public SearchManagerImpl(AdsServiceClient adsServiceClient, GeolocationServiceClient geolocationServiceClient) {
        this.adsServiceClient = adsServiceClient;
        this.geolocationServiceClient = geolocationServiceClient;
    }

    @Override
    public void searchByTitle(SearchByTitleRequest searchByTitleRequest, AsyncResponse asyncResponse) {
        (new Thread(() -> {
            log.info("Searching ads by title paginated, req in: {}", searchByTitleRequest.toString());
            try {
                String title = searchByTitleRequest.getTitle();
                GetAdPaginatedResponse adsResponse = getGetAdPaginatedResponse(searchByTitleRequest, title);
                List<AdResponse> ads = adsResponse.getGetAdPaginatedResponse().getAdResponse().stream().toList();
                Response response = Response.status(Response.Status.OK).entity(ads).build();
                asyncResponse.resume(response);
            } catch (ServiceUnavailableException e) {
                Response response = Response.serverError().entity(new ErrorResponse(e.getMessage())).build();
                asyncResponse.resume(response);
            }
        })).start();
    }

    private GetAdPaginatedResponse getGetAdPaginatedResponse(SearchByTitleRequest searchByTitleRequest, String title) throws ServiceUnavailableException {
        AdsService adsService = this.adsServiceClient.getAdsService();
        AdStatus status = searchByTitleRequest.getStatus();
        String sortBy = searchByTitleRequest.getSortBy();
        String sortOrder = searchByTitleRequest.getSortOrder();
        int page = searchByTitleRequest.getPage();
        int size = searchByTitleRequest.getSize();

        GetAdsByTitlePaginated getAdsRequest = new GetAdsByTitlePaginated();
        getAdsRequest.setTitle(title);
        getAdsRequest.setStatus(status);
        getAdsRequest.setPage(page);
        getAdsRequest.setSize(size);
        getAdsRequest.setSortBy(sortBy);
        getAdsRequest.setSortOrder(sortOrder);
        GetAdPaginatedResponse adsResponse = adsService.getAdsByTitlePaginated(getAdsRequest);
        return adsResponse;
    }

    public void searchByCity(SearchByCityRequest searchByCityRequest, AsyncResponse asyncResponse) {
        (new Thread(() -> {
            log.info("Searching ads by city, req in: {}", searchByCityRequest.toString());
            try {
                Thread.sleep(1000L);
                String city = searchByCityRequest.getCity();
                BigDecimal maxPrice = searchByCityRequest.getMaxPrice();
                Integer radius = searchByCityRequest.getRadius();

                AdsService adsService = this.adsServiceClient.getAdsService();
                if (maxPrice != null && radius != null) {
                    GetAdsMaxPrice getAdsMaxPriceRequest = new GetAdsMaxPrice();
                    getAdsMaxPriceRequest.setMaxPrice(maxPrice);
                    GetAdsResponse adsResponse = adsService.getAdsMaxPrice(getAdsMaxPriceRequest);
                    log.info("Filter by maxPrice and radius");
                    List<AdResponse> ads = filterByCityAndRadius(adsResponse.getGetAdsResponse(), city, radius);
                    Response response = Response.status(Response.Status.OK).entity(ads).build();
                    asyncResponse.resume(response);
                } else if (maxPrice != null) {
                    log.info("Filter by maxPrice only");
                    GetAdsByCity getAdsByCityRequest = new GetAdsByCity();
                    getAdsByCityRequest.setCity(city);
                    getAdsByCityRequest.setMaxPrice(maxPrice);
                    GetAdsResponse adsResponse = adsService.getAdsByCity(getAdsByCityRequest);
                    List<AdResponse> ads = adsResponse.getGetAdsResponse();
                    Response response = Response.status(Response.Status.OK).entity(ads).build();
                    asyncResponse.resume(response);
                } else if (radius != null) {
                    log.info("Filter by radius only");
                    GetAdsByCity getAdsByCityRequest = new GetAdsByCity();
                    getAdsByCityRequest.setCity(city);
                    GetAdsResponse adsResponse = adsService.getAdsByCity(getAdsByCityRequest);
                    List<AdResponse> ads = filterByCityAndRadius(adsResponse.getGetAdsResponse(), city, radius);
                    Response response = Response.status(Response.Status.OK).entity(adsResponse.getGetAdsResponse()).build();
                    asyncResponse.resume(response);
                } else {
                    log.info("No filter applied");
                    GetAdsByCity getAdsByCityRequest = new GetAdsByCity();
                    getAdsByCityRequest.setCity(city);
                    GetAdsResponse adsResponse = adsService.getAdsByCity(getAdsByCityRequest);
                    List<AdResponse> ads = adsResponse.getGetAdsResponse();
                    Response response = Response.status(Response.Status.OK).entity(ads).build();
                    asyncResponse.resume(response);
                }
            } catch (InterruptedException e) {
                Response response = Response.serverError().entity(new ErrorResponse(e.getMessage())).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            } catch (ServiceUnavailableException e) {
                Response response = Response.serverError().entity(new ErrorResponse(e.getMessage())).build();
                asyncResponse.resume(response);
            }
        })).start();
    }

    public void searchByLatLong(SearchByLatLongRequest searchByLatLongRequest, AsyncResponse asyncResponse) {
        (new Thread(() -> {
            try {
                List<AdResponse> filteredAds;
                Thread.sleep(1000L);
                List<AdResponse> ads = new ArrayList<>();
                double latitude = searchByLatLongRequest.getLatitude();
                double longitude = searchByLatLongRequest.getLongitude();
                Integer radius = searchByLatLongRequest.getRadius();
                BigDecimal maxPrice = searchByLatLongRequest.getMaxPrice();
                GetAdPaginatedResponse adsResponse = getGetAdPaginatedResponse(searchByLatLongRequest);
                if (maxPrice != null) {
                    filteredAds = filterByCoordinates(adsResponse.getGetAdPaginatedResponse().getAdResponse(),
                            latitude,
                            longitude,
                            radius)
                            .stream().filter((adResponse -> adResponse.getPrice().compareTo(maxPrice)<=0)).toList();
                } else {
                    filteredAds = filterByCoordinates(adsResponse.getGetAdPaginatedResponse().getAdResponse(), latitude, longitude, radius);
                }
                Response response = Response.status(Response.Status.OK).entity(filteredAds).build();
                asyncResponse.resume(response);
            } catch (InterruptedException e) {
                Response response = Response.serverError().entity(new ErrorResponse(e.getMessage())).build();
                asyncResponse.resume(response);
                Thread.currentThread().interrupt();
            } catch (ServiceUnavailableException e) {
                Response response = Response.serverError().entity(new ErrorResponse(e.getMessage())).build();
                asyncResponse.resume(response);
            }
        })).start();
    }

    private GetAdPaginatedResponse getGetAdPaginatedResponse(SearchByLatLongRequest searchByLatLongRequest) throws ServiceUnavailableException {
        int page = searchByLatLongRequest.getPage();
        int size = searchByLatLongRequest.getSize();
        String sortBy = searchByLatLongRequest.getSortBy();
        String sortOrder = searchByLatLongRequest.getSortOrder();
        AdStatus status = searchByLatLongRequest.getStatus();

        AdsService adsService = this.adsServiceClient.getAdsService();
        GetAds getAdsRequest = new GetAds();
        getAdsRequest.setPage(page);
        getAdsRequest.setSize(size);
        getAdsRequest.setSortBy(sortBy);
        getAdsRequest.setSortOrder(sortOrder);
        getAdsRequest.setStatus(status);
        GetAdPaginatedResponse adsResponse = adsService.getAds(getAdsRequest);
        return adsResponse;
    }

    private List<AdResponse> filterByCityAndRadius(List<AdResponse> ads, String city, Integer radius) throws ServiceUnavailableException {
        GeolocationService geolocationServiceClient = this.geolocationServiceClient.getGeolocationService();
        GetLocationDetails getLocationDetails = new GetLocationDetails();
        getLocationDetails.setCity(city);
        GetLocationDetailsResponse response = geolocationServiceClient.getLocationDetails(getLocationDetails);
        double cityLatitude = response.getGetLocationDetailsResponse().getLatitude();
        double cityLongitude = response.getGetLocationDetailsResponse().getLongitude();
        System.out.println("City found: " + city + " Latitude: " + cityLatitude + " Longitude: " + cityLongitude);
        return filterByCoordinates(ads, cityLatitude, cityLongitude, radius);
    }

    private List<AdResponse> filterByCoordinates(List<AdResponse> adResponses, double latitude, double longitude, int radius) throws ServiceUnavailableException {
        GeolocationService geolocationServiceClient = this.geolocationServiceClient.getGeolocationService();
        return adResponses.stream()
                .filter(ad -> {
                    CalculateDistance calculateDistanceParameters = new CalculateDistance();
                    calculateDistanceParameters.setLat1(latitude);
                    calculateDistanceParameters.setLon1(longitude);
                    calculateDistanceParameters.setLat2(ad.getLatitude());
                    calculateDistanceParameters.setLon2(ad.getLongitude());
                    GetDistanceResponse distanceResponse = null;
                    distanceResponse = geolocationServiceClient.calculateDistance(calculateDistanceParameters);
                    double distance = distanceResponse.getGetDistanceResponse().getDistanceInKm();
                    System.out.println("Ad: " + ad.getTitle());
                    System.out.println("Distance: " + distance);
                    System.out.println("Radius: " + radius);
                    return (distance <= radius);
                }).toList();
    }
}
