package it.disim.univaq.sose.ads_service.business.impl;


import it.disim.univaq.sose.ads_service.business.AdsManager;
import it.disim.univaq.sose.ads_service.domain.Ad;
import it.disim.univaq.sose.ads_service.domain.AdStatus;
import it.disim.univaq.sose.ads_service.domain.dto.AdApproveResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdPaginatedResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdRejectResponse;
import it.disim.univaq.sose.ads_service.domain.dto.AdRequest;
import it.disim.univaq.sose.ads_service.domain.dto.AdResponse;
import it.disim.univaq.sose.ads_service.repository.AdsRepository;
import it.disim.univaq.sose.ads_service.webservice.NotFoundException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AdsManagerImpl implements AdsManager {
    private AdsRepository adsRepository;

    public AdsManagerImpl(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    /**
     * {@inheritDoc}
     */
    public AdResponse getAdDetails(Long id) throws NotFoundException {
        Ad ad = (Ad)this.adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Ad with id: " + id + " not found"));
        return new AdResponse(ad
                .getId(), ad
                .getTitle(), ad
                .getDescription(), ad
                .getSquareMeters(), ad
                .getPrice(), ad
                .getLocation(), ad
                .getLatitude(), ad
                .getLongitude(), ad
                .getStatus().toString(), ad
                .getAccountId());
    }

    /**
     * {@inheritDoc}
     */
    public AdResponse createAd(AdRequest adRequest) {
        Ad ad = new Ad();
        ad.setTitle(adRequest.getTitle());
        ad.setDescription(adRequest.getDescription());
        ad.setSquareMeters(adRequest.getSquareMeters());
        ad.setPrice(adRequest.getPrice());
        ad.setLocation(adRequest.getLocation());
        ad.setLatitude(adRequest.getLatitude());
        ad.setLongitude(adRequest.getLongitude());
        ad.setAccountId(adRequest.getAccountId());
        this.adsRepository.save(ad);
        return new AdResponse(
                ad.getId(), ad.getTitle(), ad.getDescription(), ad.getSquareMeters(), ad.getPrice(),
                ad.getLocation(), ad.getLatitude(), ad.getLongitude(), ad.getStatus().toString(),
                ad.getAccountId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AdResponse> getAdsByTitle(String searchString, AdStatus status) {
        List<Ad> ads = null;
        if (status == null) {
            ads = this.adsRepository.findAllByTitleContainsIgnoreCase(searchString);
        } else {
            ads = this.adsRepository.findAllByTitleContainsIgnoreCaseAndStatus(searchString, status);
        }
        return ads.stream()
                .map(ad -> new AdResponse(
                        ad.getId(), ad.getTitle(), ad.getDescription(), ad.getSquareMeters(), ad.getPrice(),
                        ad.getLocation(), ad.getLatitude(), ad.getLongitude(), ad.getStatus().toString(),
                        ad.getAccountId()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdPaginatedResponse getAdsByTitle(String searchString, AdStatus status, String sortBy, String sortOrder, int page, int size) {
        Sort.Direction direction;
        if (sortBy == null || sortBy.isEmpty())
            sortBy = "createdAt";
        if (sortOrder == null || sortOrder.isEmpty())
            sortOrder = "desc";
        if (page < 0)
            page = 0;
        if (size <= 0)
            size = 10;
        try {
            direction = Sort.Direction.fromString(sortOrder);
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.ASC;
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, new String[] { sortBy }));

        Page<Ad> ads = null;
        if (status == null) {
            ads = this.adsRepository.findAllByTitleContainsIgnoreCase(searchString, pageRequest);
        } else {
            ads = this.adsRepository.findAllByTitleContainsIgnoreCaseAndStatus(searchString, status, pageRequest);
        }

        return new AdPaginatedResponse(ads.stream().map(ad -> new AdResponse(
                ad.getId(), ad.getTitle(), ad.getDescription(), ad.getSquareMeters(), ad.getPrice(),
                ad.getLocation(), ad.getLatitude(), ad.getLongitude(), ad.getStatus().toString(),
                ad.getAccountId())).toList(), ads.getTotalPages(), ads.getTotalElements());
    }

    /**
     * {@inheritDoc}
     */
    public List<AdResponse> getAdsByAccountId(Long accountId) {
        List<Ad> ads = this.adsRepository.findAllByAccountIdOrderByCreatedAtDesc(accountId);
        return ads.stream()
                .map(ad -> new AdResponse(
                        ad.getId(), ad.getTitle(), ad.getDescription(), ad.getSquareMeters(), ad.getPrice(),
                        ad.getLocation(), ad.getLatitude(), ad.getLongitude(), ad.getStatus().toString(),
                        ad.getAccountId()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    public List<AdResponse> getAdsByCity(String cityName) {
        List<Ad> ads = this.adsRepository.findAllByLocationIgnoreCaseAndStatus(cityName, AdStatus.PUBLISHED);
        return ads.stream()
                .map(ad -> new AdResponse(
                        ad.getId(), ad.getTitle(), ad.getDescription(), ad.getSquareMeters(), ad.getPrice(),
                        ad.getLocation(), ad.getLatitude(), ad.getLongitude(), ad.getStatus().toString(),
                        ad.getAccountId()))

                .toList();
    }

    /**
     * {@inheritDoc}
     */
    public List<AdResponse> getAdsByCity(String cityName, BigDecimal maxPrice) {
        List<Ad> ads = this.adsRepository.findAllByLocationIgnoreCaseAndPriceIsLessThanEqualAndStatus(cityName, maxPrice, AdStatus.PUBLISHED);
        return ads.stream()
                .map(ad -> new AdResponse(
                        ad.getId(), ad.getTitle(), ad.getDescription(), ad.getSquareMeters(), ad.getPrice(),
                        ad.getLocation(), ad.getLatitude(), ad.getLongitude(), ad.getStatus().toString(),
                        ad.getAccountId()))

                .toList();
    }

    /**
     * {@inheritDoc}
     */
    public List<AdResponse> getAdsMaxPrice(BigDecimal maxPrice) {
        List<Ad> ads = this.adsRepository.findAllByPriceIsLessThanEqualAndStatus(maxPrice, AdStatus.PUBLISHED);
        return ads.stream()
                .map(ad -> new AdResponse(
                        ad.getId(), ad.getTitle(), ad.getDescription(), ad.getSquareMeters(), ad.getPrice(),
                        ad.getLocation(), ad.getLatitude(), ad.getLongitude(), ad.getStatus().toString(),
                        ad.getAccountId()))

                .toList();
    }

    /**
     * {@inheritDoc}
     */
    public AdPaginatedResponse getAds(String sortBy, String sortOrder, int page, int size, AdStatus status) {
        Sort.Direction direction;
        if (sortBy == null || sortBy.isEmpty())
            sortBy = "createdAt";
        if (sortOrder == null || sortOrder.isEmpty())
            sortOrder = "desc";
        if (page < 0)
            page = 0;
        if (size <= 0)
            size = 10;
        try {
            direction = Sort.Direction.fromString(sortOrder);
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.ASC;
        }

        Page<Ad> ads = null;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, new String[] { sortBy }));
        if (status == null) {
            ads = this.adsRepository.findAll(pageRequest);
        } else {
            ads = this.adsRepository.findAllByStatus(status, pageRequest);
        }

        List<AdResponse> adResponses = ads.stream().map(ad -> new AdResponse(
                ad.getId(), ad.getTitle(), ad.getDescription(), ad.getSquareMeters(), ad.getPrice(),
                ad.getLocation(), ad.getLatitude(), ad.getLongitude(), ad.getStatus().toString(),
                ad.getAccountId())).toList();
        return new AdPaginatedResponse(adResponses, ads.getTotalPages(), ads.getTotalElements());
    }

    /**
     * {@inheritDoc}
     */
    public AdApproveResponse approveAd(Long id) throws NotFoundException {
        Ad ad = (Ad)this.adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Ad with id: " + id + " not found"));
        ad.setApproved(true);
        ad.setStatus(AdStatus.PUBLISHED);
        this.adsRepository.save(ad);
        return new AdApproveResponse(true, "Ad approved successfully", ad.getId(), ad.getStatus());
    }

    /**
     * {@inheritDoc}
     */
    public AdRejectResponse rejectAd(Long id, String reason) throws NotFoundException {
        Ad ad = (Ad)this.adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Ad with id: " + id + " not found"));
        ad.setRejected(true);
        ad.setRejectionReason(reason);
        ad.setStatus(AdStatus.REJECTED);
        this.adsRepository.save(ad);
        return new AdRejectResponse(true, "Ad rejected successfully", id, AdStatus.REJECTED, reason);
    }

}