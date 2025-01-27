package it.disim.univaq.sose.publishservice.domain.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * The ErrorResponse class is a DTO that represents the response to an error.
 */
@Data
@XmlRootElement(name = "ErrorResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResponse {
    @XmlElement(required = true)
    private String error;


    public ErrorResponse() {}

    public ErrorResponse(String error) {
        this.error = error;
    }
}
