package it.disim.univaq.sose.ads_service.business;

import it.disim.univaq.sose.ads_service.domain.AdStatus;
import it.disim.univaq.sose.ads_service.domain.dto.AdApproveResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdPaginatedResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdRejectResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdRequest;
import it.disim.univaq.sose.ads_service.domain.dto.AdResponse;
import it.disim.univaq.sose.ads_service.webservice.NotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface AdsManager {
    /**
     * Get the details of an ad.
     * @param adId
     * @return
     * @throws NotFoundException
     */
    AdResponse getAdDetails(Long adId) throws NotFoundException;

    /**
     * Create an ad.
     * @param adRequest
     * @return
     */
    AdResponse createAd(AdRequest adRequest);

    /**
     * Get the ads by title.
     * @param searchString
     * @param status
     * @return
     */
    List<AdResponse> getAdsByTitle(String searchString, AdStatus status);

    /**
     * Get the ads by title paginated.
     * @param accountId
     * @return
     */
    List<AdResponse> getAdsByAccountId(Long accountId);

    /**
     * Get the ads by city.
     * @param cityName
     * @return
     */
    List<AdResponse> getAdsByCity(String cityName);

    /**
     * Get the ads by max price.
     * @param maxPrice
     * @return
     */
    List<AdResponse> getAdsMaxPrice(BigDecimal maxPrice);

    /**
     * Get the ads by city and max price.
     * @param cityName
     * @param maxPrice
     * @return
     */
    List<AdResponse> getAdsByCity(String cityName, BigDecimal maxPrice);

    /**
     * Get the ads paginated.
     * @param sortBy
     * @param sortOrder
     * @param page
     * @param size
     * @param status
     * @return
     */
    AdPaginatedResponse getAds(String sortBy, String sortOrder, int page, int size, AdStatus status);

    /**
     * Get the ads by title paginated.
     * @param searchString
     * @param status
     * @param sortBy
     * @param sortOrder
     * @param page
     * @param size
     * @return
     */
    AdPaginatedResponse getAdsByTitle(String searchString, AdStatus status, String sortBy, String sortOrder, int page, int size);

    /**
     * Approve an ad.
     * @param adId
     * @return
     * @throws NotFoundException
     */
    AdApproveResponse approveAd(Long adId) throws NotFoundException;

    /**
     * Reject an ad.
     * @param adId
     * @param reason
     * @return
     * @throws NotFoundException
     */
    AdRejectResponse rejectAd(Long adId, String reason) throws NotFoundException;


}