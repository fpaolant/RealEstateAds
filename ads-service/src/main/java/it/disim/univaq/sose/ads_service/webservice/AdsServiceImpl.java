package it.disim.univaq.sose.ads_service.webservice;


import it.disim.univaq.sose.ads_service.business.AdsManager;
import it.disim.univaq.sose.ads_service.domain.AdStatus;
import it.disim.univaq.sose.ads_service.domain.dto.AdApproveResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdPaginatedResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdRejectResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdRequest;
import it.disim.univaq.sose.ads_service.domain.dto.AdResponse;
import jakarta.jws.WebService;
import java.math.BigDecimal;
import java.util.List;
import org.apache.cxf.feature.Features;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The AdsServiceImpl class is the implementation of the AdsService interface.
 */
@Service
@Features(features = {"org.apache.cxf.ext.logging.LoggingFeature"})
@WebService(serviceName = "AdsService", portName = "AdsPort", targetNamespace = "http://webservice.ads_service.sose.univaq.disim.it/", endpointInterface = "it.disim.univaq.sose.ads_service.webservice.AdsService")
public class AdsServiceImpl implements AdsService {
    private static final Logger log = LoggerFactory.getLogger(it.disim.univaq.sose.ads_service.webservice.AdsServiceImpl.class);

    private final AdsManager adsManager;

    public AdsServiceImpl(AdsManager adsManager) {
        this.adsManager = adsManager;
    }

    /**
     * {@inheritDoc}
     */
    public AdResponse getAdDetails(Long id) throws NotFoundException, AdException {
        return this.adsManager.getAdDetails(id);
    }

    /**
     * {@inheritDoc}
     */
    public AdResponse createAd(AdRequest adRequest) {
        return this.adsManager.createAd(adRequest);
    }

    @Override
    public List<AdResponse> getAdsByTitle(String paramString, AdStatus status) {
        return this.adsManager.getAdsByTitle(paramString, status);
    }

    @Override
    public AdPaginatedResponse getAdsByTitlePaginated(String searchString, AdStatus status, String sortBy, String sortOrder, int page, int size) {
        return this.adsManager.getAdsByTitle(searchString, status, sortBy, sortOrder, page, size);
    }

    /**
     * {@inheritDoc}
     */
    public List<AdResponse> getAdsByAccountId(Long accountId) {
        return this.adsManager.getAdsByAccountId(accountId);
    }

    /**
     * {@inheritDoc}
     */
    public List<AdResponse> getAdsMaxPrice(BigDecimal maxPrice) {
        return this.adsManager.getAdsMaxPrice(maxPrice);
    }

    /**
     * {@inheritDoc}
     */
    public List<AdResponse> getAdsByCity(String city, BigDecimal maxPrice) {
        if (maxPrice == null) {
            System.out.println("No max price");
            return this.adsManager.getAdsByCity(city);
        }
        return this.adsManager.getAdsByCity(city, maxPrice);
    }

    /**
     * {@inheritDoc}
     */
    public AdPaginatedResponse getAds(String sortBy, String sortOrder, int page, int size, AdStatus status) {
        return this.adsManager.getAds(sortBy, sortOrder, page, size, status);
    }

    /**
     * {@inheritDoc}
     */
    public AdApproveResponse approveAd(Long id) throws NotFoundException {
        AdApproveResponse response = this.adsManager.approveAd(id);
        System.out.println("Approved Ad  " + response.toString());
        return response;
    }

    /**
     * {@inheritDoc}
     */
    public AdRejectResponse rejectAd(Long id, String reason) throws NotFoundException {
        return this.adsManager.rejectAd(id, reason);
    }
}