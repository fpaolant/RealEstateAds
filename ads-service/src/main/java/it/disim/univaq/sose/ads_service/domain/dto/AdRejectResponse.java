package it.disim.univaq.sose.ads_service.domain.dto;


import it.disim.univaq.sose.ads_service.domain.AdStatus;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

/**
 * The AdRejectResponse class is a DTO that represents the response to reject an ad.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RejectAdResponse", propOrder = {"success", "message", "adId", "status", "rejectionReason"})
public class AdRejectResponse {
    @XmlElement(required = true)
    private boolean success;

    @XmlElement
    private String message;

    @XmlElement(required = true)
    private Long adId;

    @XmlElement(required = true)
    private AdStatus status;

    @XmlElement(required = true)
    private String rejectionReason;

    public AdRejectResponse() {}

    public AdRejectResponse(boolean success, String message, Long adId, AdStatus status, String rejectionReason) {
        this.success = success;
        this.message = message;
        this.adId = adId;
        this.status = status;
        this.rejectionReason = rejectionReason;
    }

    public String toString() {
        return "AdRejectResponse(success=" + isSuccess() + ", message=" + getMessage() + ", adId=" + getAdId() + ", status=" + getStatus() + ", rejectionReason=" + getRejectionReason() + ")";
    }



}
