package it.disim.univaq.sose.publishservice.domain.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The PublishAdRequest class is a DTO that represents the request to publish an ad.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublishAdRequest", propOrder = {"title", "description", "squareMeters", "price", "location", "latitude", "longitude", "accountId"})
public class PublishAdRequest {
    @XmlElement(required = true)
    private String title;

    private String description;

    @XmlElement(required = true)
    private Integer squareMeters;

    @XmlElement(required = true)
    private BigDecimal price;

    @XmlElement(required = true)
    private String location;

    @XmlElement(required = true)
    private Double latitude;

    @XmlElement(required = true)
    private Double longitude;

    @XmlElement(required = true)
    private Long accountId;

    public PublishAdRequest() {}

    public PublishAdRequest(String title, String description, Integer squareMeters, BigDecimal price, String location, Double latitude, Double longitude, Long accountId) {
        this.title = title;
        this.description = description;
        this.squareMeters = squareMeters;
        this.price = price;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accountId = accountId;
    }

    public String toString() {
        return "PublishAdRequest(title=" + getTitle() + ", description=" + getDescription() + ", squareMeters=" + getSquareMeters() + ", price=" + getPrice() + ", location=" + getLocation() + ", latitude=" + getLatitude() + ", longitude=" + getLongitude() + ", accountId=" + getAccountId() + ")";
    }
}