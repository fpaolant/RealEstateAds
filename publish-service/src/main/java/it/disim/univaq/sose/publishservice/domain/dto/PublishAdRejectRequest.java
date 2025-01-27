package it.disim.univaq.sose.publishservice.domain.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;


/**
 * The PublishAdRejectRequest class is a DTO that represents the request to reject an ad.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublishAdRejectRequest")
public class PublishAdRejectRequest {
    @XmlElement(required = true)
    private String reason;

    public PublishAdRejectRequest() {}

    public PublishAdRejectRequest(String reason) {
        this.reason = reason;
    }


}