package it.disim.univaq.sose.account_service.repository;

import it.disim.univaq.sose.account_service.domain.Account;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String paramString);
}
