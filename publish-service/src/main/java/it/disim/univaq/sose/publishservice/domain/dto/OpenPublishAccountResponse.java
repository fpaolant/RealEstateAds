package it.disim.univaq.sose.publishservice.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.UUID;

@Data
@XmlRootElement(name = "OpenPublishAccountResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class OpenPublishAccountResponse {
    private static final long serialVersionUID = 4592896323731902686L;

    @XmlElement(required = true)
    private Long id;

    @XmlElement(required = true)
    private String username;

    @XmlElement(required = true)
    private UUID walletId;
}
