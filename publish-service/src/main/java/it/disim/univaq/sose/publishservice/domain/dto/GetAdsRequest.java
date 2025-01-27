package it.disim.univaq.sose.publishservice.domain.dto;


import it.disim.univaq.sose.ads_service.webservice.AdStatus;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The GetAdsRequest class is a DTO that represents the request to publish an ad.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAdsRequest", propOrder = {})
public class GetAdsRequest {
    @XmlElement(required = true)
    private int page;

    @XmlElement(required = true)
    private int size;

    private String sortBy;

    private String sortOrder;

    private AdStatus status;

    public GetAdsRequest() {
    }

    public GetAdsRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public GetAdsRequest(int page, int size, String sortBy, String sortOrder, AdStatus status) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.status = status;
    }

    public String toString() {
        return "GetAdsRequest(page=" + getPage() + ", size=" + getSize() + ", sortBy=" + getSortBy() + ", sortOrder=" + getSortOrder() + ", status=" + getStatus() + ")";
    }

}