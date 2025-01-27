package it.disim.univaq.sose.publishservice.domain.dto;

import it.disim.univaq.sose.ads_service.webservice.AdStatus;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;



/**
 * The PublishAdRejectResponse class is a DTO that represents the response to reject an ad.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublishAdApproveResponse", propOrder = {"success", "message", "adId", "status", "rejectionReason"})
public class PublishAdRejectResponse {
    @XmlElement(required = true)
    private boolean success;

    @XmlElement(required = true)
    private String message;

    @XmlElement(required = true)
    private Long adId;

    @XmlElement(required = true)
    private AdStatus status;

    @XmlElement(required = true)
    private String rejectionReason;

    public PublishAdRejectResponse() {
    }
    public PublishAdRejectResponse(boolean success, String message, Long adId, AdStatus status, String rejectionReason) {
        this.success = success;
        this.message = message;
        this.adId = adId;
        this.status = status;
        this.rejectionReason = rejectionReason;
    }
}
