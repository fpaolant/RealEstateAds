package it.disim.univaq.sose.ads_service.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAdsResponse", propOrder = {"getAdsResponse"})
public class GetAdsResponse {
    @XmlElement(name = "GetAdsResponse", namespace = "http://webservice.ads_service.sose.univaq.disim.it/")
    protected List<AdResponse> getAdsResponse;

    public List<AdResponse> getAdsResponse() {
        if (this.getAdsResponse == null)
            this.getAdsResponse = new ArrayList<>();
        return this.getAdsResponse;
    }
}
