package it.disim.univaq.sose.ads_service.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAdDetailsResponse", propOrder = {"getAdDetailsResponse"})
public class GetAdDetailsResponse {
    @XmlElement(name = "GetAdDetailsResponse", namespace = "http://service.ads_service.sose.univaq.disim.it/")
    protected AdResponse getAdDetailsResponse;

    public AdResponse getGetAdDetailsResponse() {
        return this.getAdDetailsResponse;
    }

    public void setGetAdDetailsResponse(AdResponse value) {
        this.getAdDetailsResponse = value;
    }
}
