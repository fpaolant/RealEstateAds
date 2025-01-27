package it.disim.univaq.sose.ads_service.domain.dto;


import it.disim.univaq.sose.ads_service.domain.AdStatus;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

/**
 * The AdApproveResponse class is a DTO that represents the response to approve an ad.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApproveAdResponse", propOrder = {"success", "message", "adId", "status"})
public class AdApproveResponse {
    @XmlElement(required = true)
    private boolean success;

    @XmlElement(required = true)
    private String message;

    @XmlElement(required = true)
    private Long adId;

    @XmlElement(required = true)
    private AdStatus status;

    public AdApproveResponse() {
    }
    public AdApproveResponse(boolean success, String message, Long adId, AdStatus status) {
        this.success = success;
        this.message = message;
        this.adId = adId;
        this.status = status;
    }
}