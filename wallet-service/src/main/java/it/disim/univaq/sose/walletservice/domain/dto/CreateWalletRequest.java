package it.disim.univaq.sose.walletservice.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "CreateWalletRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateWalletRequest {
    @XmlElement(required = true)
    private Long accountId;

    public CreateWalletRequest() {
    }

    public CreateWalletRequest(Long accountId) {
        this.accountId = accountId;
    }
}
