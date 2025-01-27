package it.disim.univaq.sose.geolocation_service.config;


import it.disim.univaq.sose.geolocation_service.webservice.GeolocationService;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.WebServiceFeature;
import org.apache.cxf.Bus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.metrics.MetricsFeature;
import org.apache.cxf.metrics.MetricsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CXFConfig {
    private final Bus bus;

    private final GeolocationService geolocationService;

    private final MetricsProvider metricsProvider;

    public CXFConfig(Bus bus, GeolocationService geolocationService, MetricsProvider metricsProvider) {
        this.bus = bus;
        this.geolocationService = geolocationService;
        this.metricsProvider = metricsProvider;
    }

    @Bean
    public LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        return loggingFeature;
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(this.bus, this.geolocationService, null, null, (WebServiceFeature[])new MetricsFeature[] { new MetricsFeature(this.metricsProvider) });
        endpoint.publish("/GeolocationService");
        return (Endpoint)endpoint;
    }
}