package it.disim.univaq.sose.search_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.apache.cxf.jaxrs.swagger.ui.SwaggerUiConfig;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * The ApacheCXFConfig class configures Apache CXF for use with Spring Boot.
 */
@Configuration
public class ApacheCXFConfig {

    @Value("${cxf.path}")
    private String cxfPath;

    /**
     * Configures a logging feature for CXF to provide detailed logging of requests and responses.
     * The logs are formatted for better readability.
     *
     * @return the configured LoggingFeature
     */
    @Bean
    public LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        return loggingFeature;
    }

    /**
     * Configures a Jackson JSON provider for use with CXF.
     * This provider handles JSON serialization and deserialization.
     *
     * @return the configured JacksonJsonProvider
     */
    @Bean
    public JacksonJsonProvider jsonProvider() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new JacksonJsonProvider(objectMapper);
    }

    /**
     * Configures the OpenAPI feature for CXF, which generates and serves the OpenAPI documentation
     * for the REST endpoints in this service.
     *
     * @return the configured OpenApiFeature
     */
    @Bean
    public OpenApiFeature createOpenApiFeature() {
        OpenApiFeature openApiFeature = new OpenApiFeature();
        openApiFeature.setPrettyPrint(true);
        openApiFeature.setTitle("Search Ads Service API");
        openApiFeature.setContactName("System team");
        openApiFeature.setDescription("This is Search Ads Service. Uses Apache CXF and Spring Boot on JAX-RS.");
        openApiFeature.setPrettyPrint(true);
        openApiFeature.setVersion("0.0.1-SNAPSHOT");
        openApiFeature.setSwaggerUiConfig((new SwaggerUiConfig())
                .url(this.cxfPath + "/openapi.json").queryConfigEnabled(false));
        return openApiFeature;
    }

    @Bean
    public JAXRSServerFactoryBean cxfServer(OpenApiFeature openApiFeature) {
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setAddress(this.cxfPath);
        factoryBean.setFeatures(Collections.singletonList(openApiFeature));
        return factoryBean;
    }
}
