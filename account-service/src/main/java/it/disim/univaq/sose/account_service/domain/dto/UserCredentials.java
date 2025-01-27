package it.disim.univaq.sose.account_service.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * The UserCredentials class is a DTO that represents the credentials of a user.
 */
@Data
@XmlRootElement(name = "UserCredentials")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserCredentials {
    @XmlElement(required = true)
    private String username;

    @XmlElement(required = true)
    private String password;

    public UserCredentials() {}

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
