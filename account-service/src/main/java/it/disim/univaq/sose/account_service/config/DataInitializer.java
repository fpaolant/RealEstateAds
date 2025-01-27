package it.disim.univaq.sose.account_service.config;


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
        // Controlla se ci sono record nella tabella "account"
        Long count = (Long) entityManager.createQuery("SELECT COUNT(a) FROM Account a")
                .getSingleResult();

        if (count == 0) {
            // Inserisci i dati iniziali
            entityManager.createNativeQuery("INSERT INTO `account` (`id`, `created_at`, `updated_at`, `email`, `mobile`, `name`, `password`, `role`, `surname`, `username`) VALUES\n" +
                            "(1, '2025-01-20 09:32:50.273974', '2025-01-20 09:32:50.273974', 'paolo.rossi@realestateads.it', '3311234567', 'Paolo', '$2a$10$QdOqGH45vVcsvJu2zGx9iuW0XFBdOpHSAXmwCGtUBdnbgz7QvGQBS', 0, 'Bianchi', 'admin'),\n" +
                            "(2, '2025-01-21 12:45:50.581092', '2025-01-23 18:50:53.108538', 'mario.rossi@realestateads.it', '3311234567', 'Mario', '$2a$10$7R29RZxFyHBqLHGl7yN.tuTmMVIBad0n8IlTdC/XdoOLFyLq6nv6q', 1, 'Rossi', 'user');")
                    .executeUpdate();
        }
    }
}