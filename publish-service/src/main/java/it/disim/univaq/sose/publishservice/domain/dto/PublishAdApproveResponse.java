package it.disim.univaq.sose.publishservice.domain.dto;

import it.disim.univaq.sose.ads_service.webservice.AdStatus;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublishAdApproveResponse", propOrder = {"success", "message", "adId", "status"})
public class PublishAdApproveResponse {
    @XmlElement(required = true)
    private boolean success;

    @XmlElement(required = true)
    private String message;

    @XmlElement(required = true)
    private Long adId;

    @XmlElement(required = true)
    private AdStatus status;

    public PublishAdApproveResponse() {
    }
    public PublishAdApproveResponse(boolean success, String message, Long adId, AdStatus status) {
        this.success = success;
        this.message = message;
        this.adId = adId;
        this.status = status;
    }
}
