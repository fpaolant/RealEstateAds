package it.disim.univaq.sose.geolocation_service.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * The GetDistanceResponse class is a DTO that represents the response of the distance between two locations.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDistanceResponse", propOrder = {"getDistanceResponse"})
public class GetDistanceResponse {
    @XmlElement(name = "GetDistanceResponse", namespace = "http://webservice.geolocation_service.sose.univaq.disim.it/")
    protected DistanceResponse getDistanceResponse;

    public DistanceResponse getGetDistanceResponse() {
        return this.getDistanceResponse;
    }

    public void setGetDistanceResponse(DistanceResponse value) {
        this.getDistanceResponse = value;
    }
}
