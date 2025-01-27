package it.disim.univaq.sose.geolocation_service.webservice;

import it.disim.univaq.sose.geolocation_service.domain.dto.DistanceResponse;
import it.disim.univaq.sose.geolocation_service.domain.dto.LocationDetailsResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.ws.ResponseWrapper;
import java.util.concurrent.CompletableFuture;

/**
 * The GeolocationService interface is a web service that provides the operations
 * to get the location details and calculate the distance between two locations.
 */
@WebService(name = "GeolocationService")
public interface GeolocationService {
    /**
     * The getLocationDetails operation returns the location details of a city.
     *
     * @param paramString the city name
     * @return the location details of the city
     */
    @WebResult(name = "GetLocationDetailsResponse",
            targetNamespace = "http://webservice.geolocation_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:GetLocationDetails")
    @ResponseWrapper(localName = "getLocationDetailsResponse",
            className = "it.disim.univaq.sose.geolocation_service.domain.dto.GetLocationDetailsResponse")
    LocationDetailsResponse getLocationDetails(@XmlElement(required = true) @WebParam(name = "city",
            targetNamespace = "http://webservice.geolocation_service.sose.univaq.disim.it/") String paramString);

    /**
     * The getLocationDetailsAsync operation returns the location details of a city asynchronously.
     *
     * @param paramString the city name
     * @return the location details of the city
     */
    @WebResult(name = "getLocationDetailsResponse",
            targetNamespace = "http://webservice.geolocation_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:GetLocationDetailsAsync")
    @ResponseWrapper(localName = "getLocationDetailsResponse",
            className = "it.disim.univaq.sose.geolocation_service.domain.dto.LocationDetailsResponse")
    CompletableFuture<LocationDetailsResponse> getLocationDetailsAsync(String paramString);

    /**
     * The calculateDistance operation returns the distance between two locations.
     *
     * @param paramDouble1 the latitude of the first location
     * @param paramDouble2 the longitude of the first location
     * @param paramDouble3 the latitude of the second location
     * @param paramDouble4 the longitude of the second location
     * @return the distance between the two locations
     */
    @WebResult(name = "GetDistanceResponse",
            targetNamespace = "http://webservice.geolocation_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:CalculateDistance")
    @ResponseWrapper(localName = "getDistanceResponse",
            className = "it.disim.univaq.sose.geolocation_service.domain.dto.GetDistanceResponse")
    DistanceResponse calculateDistance(@XmlElement(required = true) @WebParam(name = "lat1", targetNamespace = "http://webservice.geolocation_service.sose.univaq.disim.it/") double paramDouble1, @XmlElement(required = true) @WebParam(name = "lon1", targetNamespace = "http://webservice.geolocation_service.sose.univaq.disim.it/") double paramDouble2, @XmlElement(required = true) @WebParam(name = "lat2", targetNamespace = "http://webservice.geolocation_service.sose.univaq.disim.it/") double paramDouble3, @XmlElement(required = true) @WebParam(name = "lon2", targetNamespace = "http://webservice.geolocation_service.sose.univaq.disim.it/") double paramDouble4);
}
