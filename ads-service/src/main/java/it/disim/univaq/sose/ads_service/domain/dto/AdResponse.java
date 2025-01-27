package it.disim.univaq.sose.ads_service.domain.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The AdResponse class is a DTO that represents the response to an ad.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdResponse", propOrder = {"id", "title", "description", "squareMeters", "price", "location", "latitude", "longitude", "status", "accountId"})
public class AdResponse {
    @XmlElement(required = true)
    private Long id;

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
    private String status;

    @XmlElement(required = true)
    private Long accountId;

    public AdResponse() {
    }

    public AdResponse(Long id, String title, String description, Integer squareMeters, BigDecimal price, String location, Double latitude, Double longitude, String status, Long accountId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.squareMeters = squareMeters;
        this.price = price;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.accountId = accountId;
    }

    public String toString() {
        return "AdResponse(id=" + getId() + ", title=" + getTitle() + ", description=" + getDescription() + ", squareMeters=" + getSquareMeters() + ", price=" + getPrice() + ", location=" + getLocation() + ", latitude=" + getLatitude() + ", longitude=" + getLongitude() + ", status=" + getStatus() + ", accountId=" + getAccountId() + ")";
    }

}
