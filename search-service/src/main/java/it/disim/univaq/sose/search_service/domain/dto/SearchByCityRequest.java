package it.disim.univaq.sose.search_service.domain.dto;


import it.disim.univaq.sose.ads_service.webservice.AdStatus;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The SearchByCityRequest class is a DTO that represents the request to search ads by city.
 */
@Data
@XmlRootElement(name = "SearchByCityRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchByCityRequest {
    @XmlElement(required = true)
    private String city;

    @XmlElement
    private BigDecimal maxPrice;

    @XmlElement
    private Integer radius;

    private AdStatus status;

    private String sortBy;

    private String sortOrder;

    private int page;

    private int size;

    public SearchByCityRequest() {}

    public SearchByCityRequest(String city, BigDecimal maxPrice, Integer radius) {
        this.city = city;
        this.maxPrice = maxPrice;
        this.radius = radius;
    }

    public SearchByCityRequest(String city, BigDecimal maxPrice, Integer radius, AdStatus status, String sortBy,
                               String sortOrder, int page, int size) {
        this.city = city;
        this.maxPrice = maxPrice;
        this.radius = radius;
        this.status = status;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = page;
        this.size = size;
    }


}
