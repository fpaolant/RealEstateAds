package it.disim.univaq.sose.account_service.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for open account requests.
 */
@Data
@XmlRootElement(name = "OpenAccountRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class OpenAccountRequest {
    @XmlElement(required = true)
    private String username;

    @XmlElement(required = true)
    private String password;

    @XmlElement(required = true)
    private String email;

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private String surname;

    @XmlElement(required = true)
    private String mobile;

    public OpenAccountRequest() {
    }

    public OpenAccountRequest(String name, String surname, String username, String password, String email, String mobile) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }
}