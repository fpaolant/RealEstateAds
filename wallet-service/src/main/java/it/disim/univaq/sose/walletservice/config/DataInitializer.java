package it.disim.univaq.sose.walletservice.config;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class DataInitializer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {
        // Controlla se ci sono record nella tabella "wallet"
        Long count = (Long) entityManager.createQuery("SELECT COUNT(w) FROM Wallet w")
                .getSingleResult();

        if (count == 0) {
            String sql = "INSERT INTO `wallet` (id, created_at, updated_at, account_id, balance, wallet_id) " +
                    "VALUES " +
                    "(1, '2025-01-24 16:00:00', '2025-01-24 16:05:00', 1, 500.00, UNHEX(REPLACE(UUID(), '-', ''))), " +
                    "(2, '2025-01-24 16:10:00', '2025-01-24 16:15:00', 2, 1000.75, UNHEX(REPLACE(UUID(), '-', '')));";

            // Inserisci i dati iniziali
            entityManager.createNativeQuery(sql)
                    .executeUpdate();
        }
    }
}