package it.disim.univaq.sose.ads_service.config;


import it.disim.univaq.sose.ads_service.webservice.AdsService;
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

    private final AdsService adsService;

    private final MetricsProvider metricsProvider;

    public CXFConfig(Bus bus, AdsService adsService, MetricsProvider metricsProvider) {
        this.bus = bus;
        this.adsService = adsService;
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
        EndpointImpl endpoint = new EndpointImpl(this.bus, this.adsService, null, null, (WebServiceFeature[])new MetricsFeature[] { new MetricsFeature(this.metricsProvider) });
        endpoint.publish("/AdsService");
        return (Endpoint)endpoint;
    }
}
