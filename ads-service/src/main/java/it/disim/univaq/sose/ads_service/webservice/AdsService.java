package it.disim.univaq.sose.ads_service.webservice;


import it.disim.univaq.sose.ads_service.domain.AdStatus;
import it.disim.univaq.sose.ads_service.domain.dto.AdApproveResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdPaginatedResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdRejectResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdRequest;
import it.disim.univaq.sose.ads_service.domain.dto.AdResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.ws.ResponseWrapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * The AdsService interface is a web service that exposes the operations to manage ads.
 */
@WebService(name = "AdsService")
public interface AdsService {
    /**
     * Get the details of an ad.
     *
     * @param adId the id of the ad
     * @return the details of the ad
     * @throws NotFoundException if the ad is not found
     * @throws AdException if an error occurs
     */
    @WebResult(name = "GetAdDetailsResponse", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:GetAdDetails")
    @ResponseWrapper(localName = "getAdDetailsResponse",
            className = "it.disim.univaq.sose.ads_service.domain.dto.GetAdDetailsResponse")
    AdResponse getAdDetails(@XmlElement(required = true) @WebParam(name = "id",
            targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") Long adId)
            throws NotFoundException, AdException;

    /** Create an ad.
     *
     * @param adRequest the request to create an ad
     * @return the response of the creation
     */
    @WebResult(name = "GetAdResponse", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:createAd")
    @ResponseWrapper(localName = "getAdResponse",
            className = "it.disim.univaq.sose.ads_service.domain.dto.GetAdResponse")
    AdResponse createAd(@XmlElement(required = true) @WebParam(name = "adRequest",
            targetNamespace = "http://webservice.ads_service.sose.univaq.disim,it/") AdRequest adRequest);

    /**
     * Get the ads by title.
     * @param title
     * @param status
     * @return
     */
    @WebResult(name = "GetAdsResponse", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:getAdsByTitle")
    @ResponseWrapper(localName = "getAdsResponse", className = "it.disim.univaq.sose.ads_service.domain.dto.GetAdsResponse")
    List<AdResponse> getAdsByTitle(
            @XmlElement(required = true) @WebParam(name = "title", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") String title,
            @XmlElement(required = false) @WebParam(name = "status", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") AdStatus status);

    /** Get the ads by title paginated.
     *
     * @param title the title of the ad
     * @param status the status of the ad
     * @param sortBy the field to sort by
     * @param sortOrder the order of the sort
     * @param page the page number
     * @param size the size of the page
     * @return the ads paginated
     */
    @WebResult(name = "GetAdPaginatedResponse", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:getAdsByTitlePaginated")
    @ResponseWrapper(localName = "getAdPaginatedResponse", className = "it.disim.univaq.sose.ads_service.domain.dto.GetAdPaginatedResponse")
    AdPaginatedResponse getAdsByTitlePaginated(
            @XmlElement(required = true) @WebParam(name = "title", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") String title,
            @WebParam(name = "status", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") AdStatus status,
            @WebParam(name = "sortBy") String sortBy,
            @WebParam(name = "sortOrder") String sortOrder,
            @WebParam(name = "page") int page,
            @WebParam(name = "size") int size);


    /**
     * Get the ads of an account.
     * @param accountId
     * @return
     */
    @WebResult(name = "GetAdsResponse", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:GetAdsByAccountId")
    @ResponseWrapper(localName = "getAdsResponse", className = "it.disim.univaq.sose.ads_service.domain.dto.GetAdsResponse")
    List<AdResponse> getAdsByAccountId(@XmlElement(required = true) @WebParam(name = "accountId", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") Long accountId);

    /**
     * Get the ads with a maximum price.
     * @param maxPrice
     * @return
     */
    @WebResult(name = "GetAdsResponse", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:GetAdsMaxPrice")
    @ResponseWrapper(localName = "getAdsResponse", className = "it.disim.univaq.sose.ads_service.domain.dto.GetAdsResponse")
    List<AdResponse> getAdsMaxPrice(
            @XmlElement(required = true) @WebParam(name = "maxPrice", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") BigDecimal maxPrice);

    /**
     * Get the ads of a city.
     * @param cityName
     * @param maxPrice
     * @return
     */
    @WebResult(name = "GetAdsResponse", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:getAdsByCity")
    @ResponseWrapper(localName = "getAdsResponse", className = "it.disim.univaq.sose.ads_service.domain.dto.GetAdsResponse")
    List<AdResponse> getAdsByCity(
            @XmlElement(required = true) @WebParam(name = "city", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") String cityName,
            @WebParam(name = "maxPrice", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") BigDecimal maxPrice);

    /**
     * Get the ads paginated.
     * @param sortBy
     * @param sortOrder
     * @param page
     * @param size
     * @param status
     * @return
     */
    @WebResult(name = "GetAdPaginatedResponse", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:GetAds")
    @ResponseWrapper(localName = "getAdPaginatedResponse", className = "it.disim.univaq.sose.ads_service.domain.dto.GetAdPaginatedResponse")
    AdPaginatedResponse getAds(
            @WebParam(name = "sortBy") String sortBy,
            @WebParam(name = "sortOrder") String sortOrder,
            @WebParam(name = "page") int page,
            @WebParam(name = "size") int size,
            @WebParam(name = "status") AdStatus status
    );

    /**
     * Approve an ad.
     * @param adId
     * @return
     * @throws NotFoundException
     */
    @WebResult(name = "GetAdApproveResponse", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:ApproveAd")
    @ResponseWrapper(localName = "getAdApproveResponse", className = "it.disim.univaq.sose.ads_service.domain.dto.GetAdApproveResponse")
    AdApproveResponse approveAd(@XmlElement(required = true) @WebParam(name = "id", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") Long adId) throws NotFoundException;

    /**
     * Reject an ad.
     * @param adId
     * @param reason
     * @return
     * @throws NotFoundException
     */
    @WebResult(name = "GetAdRejectResponse", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    @WebMethod(action = "urn:RejectAd")
    @ResponseWrapper(localName = "getAdRejectResponse", className = "it.disim.univaq.sose.ads_service.domain.dto.GetAdRejectResponse")
    AdRejectResponse rejectAd(@XmlElement(required = true) @WebParam(name = "id", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") Long adId, @XmlElement(required = true) @WebParam(name = "reason", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/") String reason) throws NotFoundException;
}
