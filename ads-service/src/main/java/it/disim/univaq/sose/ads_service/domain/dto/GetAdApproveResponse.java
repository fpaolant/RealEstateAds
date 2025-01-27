package it.disim.univaq.sose.ads_service.domain.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAdApproveResponse", propOrder = {"getAdApproveResponse"})
public class GetAdApproveResponse {
    @XmlElement(name = "GetAdApproveResponse", namespace = "http://service.ads_service.sose.univaq.disim.it/")
    protected AdApproveResponse getAdApproveResponse;

    public AdApproveResponse getAdApproveResponse() {
        return this.getAdApproveResponse;
    }

    public void setGetAdApproveResponse(AdApproveResponse value) {
        this.getAdApproveResponse = value;
    }
}
