package it.disim.univaq.sose.publishservice.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "publishGetAdsResponse", propOrder = {"getAdsResponse"})
public class PublishGetAdsResponse {
    @XmlElement(name = "PublishGetAdsResponse", namespace = "http://webservice.publishservice.sose.univaq.disim.it/")
    protected List<AdResponse> getAdsResponse;

    public List<AdResponse> getAdsResponse() {
        if (this.getAdsResponse == null)
            this.getAdsResponse = new ArrayList<>();
        return this.getAdsResponse;
    }

    public void setAdsResponse(List<AdResponse> ads) {
        this.getAdsResponse = ads;
    }
}
