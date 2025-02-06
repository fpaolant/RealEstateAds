package it.disim.univaq.sose.publishservice.domain.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "PublishAdStatusResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class PublishAdStatusResponse {
    @XmlElement(required = true)
    private String status;

    public String toString() {
        return "PublishAdStatusResponse(status=" + this.getStatus() + ")";
    }
}


