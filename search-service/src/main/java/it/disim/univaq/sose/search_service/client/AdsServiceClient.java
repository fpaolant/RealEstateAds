package it.disim.univaq.sose.search_service.client;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import it.disim.univaq.sose.ads_service.webservice.AdsService;
import it.disim.univaq.sose.ads_service.webservice.AdsService_Service;
import it.disim.univaq.sose.search_service.service.ServiceUnavailableException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AdsServiceClient {
    private static final Logger log = LoggerFactory.getLogger(it.disim.univaq.sose.search_service.client.AdsServiceClient.class);

    private volatile AdsService_Service adsService;

    private final EurekaClient eurekaClient;

    private final AtomicReference<URL> lastUrl = new AtomicReference<>();

    private final List<InstanceInfo> lastInstancesCache = Collections.synchronizedList(new ArrayList<>());

    public AdsServiceClient(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
        this.adsService = new AdsService_Service();
    }

    public AdsService getAdsService() throws ServiceUnavailableException {
        try {
            List<InstanceInfo> instances = Optional.<List<InstanceInfo>>ofNullable(this.eurekaClient.getInstancesByVipAddress("ADS-SERVICE", false)).filter(list -> !list.isEmpty()).orElseGet(() -> {
                log.warn("Using cached instances for ADS-SERVICE");
                log.warn("lastInstancesCache {}", this.lastInstancesCache);
                synchronized (this.lastInstancesCache) {
                    return new ArrayList<>(this.lastInstancesCache);
                }
            });
            if (instances == null || instances.isEmpty()) {
                log.error("No instances available for ADS-SERVICE");
                throw new ServiceUnavailableException("No instances available for ADS-SERVICE");
            }
            synchronized (this.lastInstancesCache) {
                this.lastInstancesCache.clear();
                this.lastInstancesCache.addAll(instances);
            }
            URL lastUrlValue = this.lastUrl.get();
            if (lastUrlValue != null)
                instances.removeIf(instance -> {
                    try {
                        return (new URL(instance.getHomePageUrl() + "services/AdsService?wsdl")).equals(lastUrlValue);
                    } catch (Exception e) {
                        log.error("Malformed URL while filtering instances: {}", e.getMessage(), e);
                        return false;
                    }
                });
            if (instances.isEmpty()) {
                log.warn("No alternative instances available for ADS-SERVICE, using the last one");
                if (this.adsService != null)
                    return this.adsService.getAdsPort();
                throw new ServiceUnavailableException("No instances available for ADS-SERVICE");
            }
            Collections.shuffle(instances);
            InstanceInfo instance = instances.get(0);
            String eurekaURL = instance.getHomePageUrl() + "services/AdsService?wsdl";
            URL url = new URL(eurekaURL);
            this.adsService = new AdsService_Service(url);
            log.info("Nre AdsService URL: {}", url);
            this.lastUrl.set(url);
            return this.adsService.getAdsPort();
        } catch (MalformedURLException e) {
            log.error("Malformed URL: {}", e.getMessage(), e);
            throw new ServiceUnavailableException("Malformed URL: " + e.getMessage());
        } catch (Exception e) {
            log.error("Failed to retrieve AdsService URL: {}", e.getMessage(), e);
            throw new ServiceUnavailableException("Failed to retrieve AdsService URL: " + e.getMessage());
        }
    }

    private List<InstanceInfo> deepCopyInstanceInfoList(List<InstanceInfo> instances) {
        return (List<InstanceInfo>)instances.stream()
                .map(InstanceInfo::new)
                .collect(Collectors.toList());
    }
}
