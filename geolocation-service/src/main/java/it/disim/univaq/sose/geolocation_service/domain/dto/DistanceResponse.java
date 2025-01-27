package it.disim.univaq.sose.geolocation_service.domain.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * The DistanceResponse class is a DTO that represents the response of the distance between two locations.
 */
@Data
@XmlRootElement(name = "DistanceResponse")
public class DistanceResponse {
    private double distanceInKm;


    public DistanceResponse() {}

    public DistanceResponse(double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }
}
