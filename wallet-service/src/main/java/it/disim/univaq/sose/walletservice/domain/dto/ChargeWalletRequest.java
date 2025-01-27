package it.disim.univaq.sose.walletservice.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@XmlRootElement(name = "ChargeWalletRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChargeWalletRequest {
    @XmlElement(required = true)
    private UUID walletId;
    @XmlElement(required = true)
    private BigDecimal amount;

    public ChargeWalletRequest() {
    }

    public ChargeWalletRequest(UUID walletId, BigDecimal amount) {
        this.walletId = walletId;
        this.amount = amount;
    }
}
