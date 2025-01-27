package it.disim.univaq.sose.publishservice.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpenPublishAccountRequest")
public class OpenPublishAccountRequest {
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

    public OpenPublishAccountRequest() {
    }

    public OpenPublishAccountRequest(String name, String surname, String username, String password, String email, String mobile) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }

}
