package it.disim.univaq.sose.geolocation_service.domain.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * The LocationDetailsResponse class is a DTO that represents the response of the location details.
 */
@Data
@XmlRootElement(name = "LocationDetails")
public class LocationDetailsResponse {
    private String city;

    private String country;

    private double latitude;

    private double longitude;

    public LocationDetailsResponse(String city, String country, double latitude, double longitude) {
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationDetailsResponse() {}
}