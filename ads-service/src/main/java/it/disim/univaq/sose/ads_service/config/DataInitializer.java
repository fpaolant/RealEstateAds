package it.disim.univaq.sose.ads_service.config;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.core.io.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DataInitializer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    // Carica il file SQL dalla directory src/main/resources
    @Value("classpath:initial_data.sql")
    private Resource sqlFile;

    @Override
    @Transactional
    public void run(String... args) {
        try {
            // Leggi la query dal file SQL
            String sqlQuery = readSqlFile();

            // Controlla se ci sono record nella tabella "ads"
            Long count = (Long) entityManager.createQuery("SELECT COUNT(a) FROM Ad a")
                    .getSingleResult();

            if (count == 0) {
                // Esegui la query per inserire i dati iniziali
                entityManager.createNativeQuery(sqlQuery).executeUpdate();
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore nella lettura del file SQL", e);
        }
    }

    private String readSqlFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(sqlFile.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }
}