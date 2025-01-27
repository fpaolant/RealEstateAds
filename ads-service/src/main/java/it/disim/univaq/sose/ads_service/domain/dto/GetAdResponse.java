package it.disim.univaq.sose.ads_service.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAdResponse", propOrder = {"getAdResponse"})
public class GetAdResponse {
    @XmlElement(name = "GetAdResponse", namespace = "http://service.ads_service.sose.univaq.disim.it/")
    protected AdResponse getAdResponse;

    public AdResponse getGetAdResponse() {
        return this.getAdResponse;
    }

    public void setGetAdResponse(AdResponse value) {
        this.getAdResponse = value;
    }
}
