package it.disim.univaq.sose.publishservice.client;

import it.disim.univaq.sose.walletservice.api.WalletServiceDefaultClient;
import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WalletServiceClient {
    private final EurekaClient eurekaClient;
    private final JacksonJsonProvider jacksonProvider;
    private final AtomicReference<String> lastUrlService = new AtomicReference<>();
    private final List<InstanceInfo> lastInstancesCache = Collections.synchronizedList(new ArrayList<>());

    public WalletServiceClient(EurekaClient eurekaClient, JacksonJsonProvider jacksonProvider) {
        this.eurekaClient = eurekaClient;
        this.jacksonProvider = jacksonProvider;
    }

    /**
     * Retrieves the URL of the WalletService from Eureka.
     *
     * @return the URL of the WalletService
     * @throws ServiceUnavailableException if no instances are available
     */
    private String getUrlServiceFromEureka() throws ServiceUnavailableException {
        try {
            List<InstanceInfo> instances = Optional.ofNullable(eurekaClient.getInstancesByVipAddress("WALLET-SERVICE", false))
                    .filter(list -> !list.isEmpty())
                    .orElseGet(() -> {
                        log.warn("Using cached instances for WALLET-SERVICE");
                        log.warn("lastInstancesCache {}", lastInstancesCache);
                        synchronized (lastInstancesCache) {
                            return new ArrayList<>(lastInstancesCache);  // Return a copy of the cached instances
                        }
                    });

            if (instances == null || instances.isEmpty()) {
                log.error("No instances available for WALLET-SERVICE");
                throw new ServiceUnavailableException("No instances available for WALLET-SERVICE");
            }

            /// Update the instance cache in a synchronized block
            synchronized (lastInstancesCache) {
                lastInstancesCache.clear();
                lastInstancesCache.addAll(deepCopyInstanceInfoList(instances));
            }

            // Remove the last used instance from the list
            String lastUrl = lastUrlService.get();
            if (lastUrl != null) {
                instances.removeIf(instance -> {
                    try {
                        return Objects.equals(new URL(instance.getHomePageUrl() + "services"), new URL(lastUrl));
                    } catch (MalformedURLException e) {
                        log.error("Malformed URL while filtering instances: {}", e.getMessage(), e);
                        return false;
                    }
                });
            }

            // If no alternative instances are available, use the last used instance
            if (instances.isEmpty()) {
                log.warn("No alternative instances available for WALLET-SERVICE, using the last used instance");
                if (lastUrl != null) {
                    return lastUrl;
                } else {
                    throw new ServiceUnavailableException("WALLET-SERVICE: No alternative instances available and no previously used instance available");
                }
            }

            // Shuffle the list to select a random instance
            Collections.shuffle(instances);
            InstanceInfo instance = instances.get(0);
            String eurekaUrl = instance.getHomePageUrl() + "services";
            log.info("New Retrieved Publish Service URL: {}", eurekaUrl);
            lastUrlService.set(eurekaUrl);

            return eurekaUrl;
        } catch (Exception e) {
            log.error("Failed to retrieve Publish URL: {}", e.getMessage(), e);
            throw new ServiceUnavailableException("Failed to retrieve Banking Operations Service URL: " + e.getMessage());
        }
    }

    /**
     * Creates a deep copy of a list of InstanceInfo objects.
     *
     * @param instances the original list of InstanceInfo objects
     * @return a deep copy of the list
     */
    private List<InstanceInfo> deepCopyInstanceInfoList(List<InstanceInfo> instances) {
        return instances.stream()
                .map(InstanceInfo::new) // Usa il costruttore di copia
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an WalletServiceDefaultClient instance.
     *
     * @return the WalletServiceDefaultClient instance
     * @throws ServiceUnavailableException if no service instances are available
     */
    public WalletServiceDefaultClient getWalletService() throws ServiceUnavailableException {
        return JAXRSClientFactory.create(getUrlServiceFromEureka(), WalletServiceDefaultClient.class, List.of(jacksonProvider));
    }

    /**
     * Retrieves a CXF Client for the WalletService.
     *
     * @return the Client instance
     * @throws ServiceUnavailableException if no service instances are available
     */
    public Client getClientWalletService() throws ServiceUnavailableException {
        WalletServiceDefaultClient api = JAXRSClientFactory.create(getUrlServiceFromEureka(), WalletServiceDefaultClient.class, List.of(jacksonProvider));
        return WebClient.client(api);
    }

    /**
     * Retrieves a WebClient for the WalletService.
     *
     * @return the WebClient instance
     * @throws ServiceUnavailableException if no service instances are available
     */
    public WebClient getWebClientWalletService() throws ServiceUnavailableException {
        WalletServiceDefaultClient api = JAXRSClientFactory.create(getUrlServiceFromEureka(), WalletServiceDefaultClient.class, List.of(jacksonProvider));
        Client client = WebClient.client(api);
        WebClient webClient = WebClient.fromClient(client);
        webClient.type(MediaType.APPLICATION_JSON);
        return webClient;
    }

    /**
     * Retrieves the ClientConfiguration for the WalletService.
     *
     * @return the ClientConfiguration instance
     * @throws ServiceUnavailableException if no service instances are available
     */
    public ClientConfiguration getClientConfigurationWalletService() throws ServiceUnavailableException {
        WalletServiceDefaultClient api = JAXRSClientFactory.create(getUrlServiceFromEureka(), WalletServiceDefaultClient.class, List.of(jacksonProvider));
        Client client = WebClient.client(api);
        return WebClient.getConfig(client);
    }

    /**
     * Retrieves the endpoint URL of the WalletService.
     *
     * @return the endpoint URL
     * @throws ServiceUnavailableException if no service instances are available
     */
    public String getEndpoint() throws ServiceUnavailableException {
        return getUrlServiceFromEureka();
    }






}
