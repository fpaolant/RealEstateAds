package it.disim.univaq.sose.publishservice.domain.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "PublishAdResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class PublishAdResponse {
    @XmlElement(required = true)
    private Long adId;
    @XmlElement(required = true)
    private String title;

    public String toString() {
        return "PublishAdResponse(adId=" + getAdId() + ")";
    }
}


