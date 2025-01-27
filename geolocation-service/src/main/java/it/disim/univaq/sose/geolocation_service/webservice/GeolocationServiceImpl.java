package it.disim.univaq.sose.geolocation_service.webservice;


import it.disim.univaq.sose.geolocation_service.domain.dto.DistanceResponse;
import it.disim.univaq.sose.geolocation_service.domain.dto.LocationDetailsResponse;
import jakarta.jws.WebService;
import java.util.concurrent.CompletableFuture;
import org.apache.cxf.feature.Features;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * The GeolocationServiceImpl class is a web service that provides the operations to get the location details and calculate the distance between two locations.
 */
@Service
@Features(features = {"org.apache.cxf.ext.logging.LoggingFeature"})
@WebService(serviceName = "GeolocationService", portName = "GeolocationPort", targetNamespace = "http://webservice.geolocation_service.sose.univaq.disim.it/", endpointInterface = "it.disim.univaq.sose.geolocation_service.webservice.GeolocationService")
public class GeolocationServiceImpl implements GeolocationService {
    private static final Logger log = LoggerFactory.getLogger(it.disim.univaq.sose.geolocation_service.webservice.GeolocationServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    public LocationDetailsResponse getLocationDetails(String city) {
        if (city.equalsIgnoreCase("Roma"))
            return new LocationDetailsResponse("Roma", "Italy", 41.9028D, 12.4964D);
        if (city.equalsIgnoreCase("Milano"))
            return new LocationDetailsResponse("Milano", "Italy", 45.4642D, 9.19D);
        if (city.equalsIgnoreCase("Firenze"))
            return new LocationDetailsResponse("Firenze", "Italy", 43.7696D, 11.2558D);
        if (city.equalsIgnoreCase("Siena"))
            return new LocationDetailsResponse("Siena", "Italy", 43.3186D, 11.3307D);
        if (city.equalsIgnoreCase("Pisa"))
            return new LocationDetailsResponse("Pisa", "Italy", 43.7228D, 10.4017D);
        if (city.equalsIgnoreCase("Napoli"))
            return new LocationDetailsResponse("Napoli", "Italy", 40.8518D, 14.2681D);
        if (city.equalsIgnoreCase("Palermo"))
            return new LocationDetailsResponse("Palermo", "Italy", 38.1157D, 13.3615D);
        if (city.equalsIgnoreCase("Catania"))
            return new LocationDetailsResponse("Catania", "Italy", 37.5079D, 15.083D);
        if (city.equalsIgnoreCase("Bologna"))
            return new LocationDetailsResponse("Bologna", "Italy", 44.4949D, 11.3426D);
        if (city.equalsIgnoreCase("Verona"))
            return new LocationDetailsResponse("Verona", "Italy", 45.4384D, 10.9916D);
        if (city.equalsIgnoreCase("Genova"))
            return new LocationDetailsResponse("Genova", "Italy", 44.4056D, 8.9463D);
        if (city.equalsIgnoreCase("Torino"))
            return new LocationDetailsResponse("Torino", "Italy", 45.0703D, 7.6869D);
        if (city.equalsIgnoreCase("Venezia"))
            return new LocationDetailsResponse("Venezia", "Italy", 45.4408D, 12.3155D);
        if (city.equalsIgnoreCase("Bari"))
            return new LocationDetailsResponse("Bari", "Italy", 41.1171D, 16.8719D);
        if (city.equalsIgnoreCase("Cagliari"))
            return new LocationDetailsResponse("Cagliari", "Italy", 39.2238D, 9.1217D);
        if (city.equalsIgnoreCase("Trieste"))
            return new LocationDetailsResponse("Trieste", "Italy", 45.6495D, 13.7768D);
        if (city.equalsIgnoreCase("Ancona"))
            return new LocationDetailsResponse("Ancona", "Italy", 43.6158D, 13.5189D);
        if (city.equalsIgnoreCase("Perugia"))
            return new LocationDetailsResponse("Perugia", "Italy", 43.1107D, 12.3892D);
        if (city.equalsIgnoreCase("L'Aquila"))
            return new LocationDetailsResponse("L'Aquila", "Italy", 42.3496D, 13.39D);
        if (city.equalsIgnoreCase("Campobasso"))
            return new LocationDetailsResponse("Campobasso", "Italy", 41.5623D, 14.6554D);
        if (city.equalsIgnoreCase("Potenza"))
            return new LocationDetailsResponse("Potenza", "Italy", 40.6395D, 15.8056D);
        if (city.equalsIgnoreCase("Catanzaro"))
            return new LocationDetailsResponse("Catanzaro", "Italy", 38.905D, 16.5941D);
        if (city.equalsIgnoreCase("Bologna"))
            return new LocationDetailsResponse("Bologna", "Italy", 44.4949D, 11.3426D);
        if (city.equalsIgnoreCase("Trento"))
            return new LocationDetailsResponse("Trento", "Italy", 46.0679D, 11.1211D);
        if (city.equalsIgnoreCase("Aosta"))
            return new LocationDetailsResponse("Aosta", "Italy", 45.7375D, 7.3152D);
        throw new RuntimeException("City not found");
    }

    /**
     * {@inheritDoc}
     */
    public DistanceResponse calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371.0D;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2.0D) * Math.sin(dLat / 2.0D) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2.0D) * Math.sin(dLon / 2.0D);
        double c = 2.0D * Math.atan2(Math.sqrt(a), Math.sqrt(1.0D - a));
        double distance = earthRadius * c;
        return new DistanceResponse(distance);
    }

    /**
     * {@inheritDoc}
     */
    public CompletableFuture<LocationDetailsResponse> getLocationDetailsAsync(String city) {
        return CompletableFuture.supplyAsync(() -> getLocationDetails(city));
    }
}