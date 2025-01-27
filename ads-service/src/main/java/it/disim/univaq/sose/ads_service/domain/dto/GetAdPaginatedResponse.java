package it.disim.univaq.sose.ads_service.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAdPaginatedResponse", propOrder = {"getAdPaginatedResponse"})
public class GetAdPaginatedResponse {
    @XmlElement(name = "GetAdPaginatedResponse", namespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    protected AdPaginatedResponse getAdPaginatedResponse;

    public AdPaginatedResponse getAdPaginatedResponse() {
        return this.getAdPaginatedResponse;
    }

    public void setGetAdPaginatedResponse(AdPaginatedResponse value) {
        this.getAdPaginatedResponse = value;
    }
}
