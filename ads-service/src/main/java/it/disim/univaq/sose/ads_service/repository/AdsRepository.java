package it.disim.univaq.sose.ads_service.repository;


import it.disim.univaq.sose.ads_service.domain.Ad;
import it.disim.univaq.sose.ads_service.domain.AdStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsRepository extends JpaRepository<Ad, Long> {
    Optional<Ad> findByIdAndAccountId(Long paramLong1, Long paramLong2);

    List<Ad> findAllByAccountIdOrderByCreatedAtDesc(Long paramLong);

    List<Ad> findAllByLocationIgnoreCaseAndStatus(String paramString, AdStatus paramAdStatus);

    List<Ad> findAllByLocationIgnoreCaseAndPriceIsLessThanEqualAndStatus(String paramString, BigDecimal paramBigDecimal, AdStatus paramAdStatus);

    List<Ad> findAllByPriceIsLessThanEqualAndStatus(BigDecimal paramBigDecimal, AdStatus paramAdStatus);

    List<Ad> findAllByTitleContainsIgnoreCase(String paramString);

    Page<Ad> findAllByTitleContainsIgnoreCase(String paramString, Pageable pageable);

    List<Ad> findAllByTitleContainsIgnoreCaseAndStatus(String paramString, AdStatus paramAdStatus);

    Page<Ad> findAllByTitleContainsIgnoreCaseAndStatus(String paramString, AdStatus paramAdStatus, Pageable pageable);

    Page<Ad> findAllByStatus(AdStatus status, Pageable pageable);
}