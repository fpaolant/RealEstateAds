package it.disim.univaq.sose.account_service.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * The TokenResponse class is a DTO that represents the response to a token request.
 */
@Data
@XmlRootElement(name = "TokenResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class TokenResponse {
    @XmlElement(required = true)
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public TokenResponse() {}

    public TokenResponse(String token) {
        this.token = token;
    }
}