package it.disim.univaq.sose.geolocation_service.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * The GetLocationDetailsResponse class is a DTO that represents the response of the location details.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLocationDetailsResponse", propOrder = {"getLocationDetailsResponse"})
public class GetLocationDetailsResponse {
    @XmlElement(name = "GetLocationDetailsResponse", namespace = "http://webservice.geolocation_service.sose.univaq.disim.it/")
    protected LocationDetailsResponse getLocationDetailsResponse;

    public LocationDetailsResponse getGetLocationDetailsResponse() {
        return this.getLocationDetailsResponse;
    }

    public void setGetLocationDetailsResponse(LocationDetailsResponse value) {
        this.getLocationDetailsResponse = value;
    }
}
