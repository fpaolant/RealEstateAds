package it.disim.univaq.sose.account_service.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.io.Serializable;

@Data
@XmlRootElement(name = "OpenAccountResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class OpenAccountResponse implements Serializable {
    private static final long serialVersionUID = 4592896323731902686L;

    @XmlElement(required = true)
    private Long id;

    @XmlElement(required = true)
    private String username;



    public OpenAccountResponse() {}

    public OpenAccountResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
