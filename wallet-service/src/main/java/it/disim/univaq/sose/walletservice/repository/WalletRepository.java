package it.disim.univaq.sose.walletservice.repository;

import com.netflix.appinfo.ApplicationInfoManager;
import it.disim.univaq.sose.walletservice.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByAccountId(Long accountId);

    Optional<Wallet> findByWalletId(UUID walletId);
}
