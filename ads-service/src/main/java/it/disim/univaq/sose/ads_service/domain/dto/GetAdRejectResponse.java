package it.disim.univaq.sose.ads_service.domain.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAdRejectResponse", propOrder = {"getAdRejectResponse"})
public class GetAdRejectResponse {
    @XmlElement(name = "GetAdRejectResponse", namespace = "http://service.ads_service.sose.univaq.disim.it/")
    protected AdRejectResponse getAdRejectResponse;

    public AdRejectResponse getAdRejectResponse() {
        return this.getAdRejectResponse;
    }

    public void setGetAdRejectResponse(AdRejectResponse value) {
        this.getAdRejectResponse = value;
    }
}