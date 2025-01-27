package it.disim.univaq.sose.walletservice.domain.dto;


import it.disim.univaq.sose.walletservice.domain.dto.adapter.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@XmlRootElement(name = "WalletResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class WalletResponse {
    private static final long serialVersionUID = 5430658314309295388L;

    @XmlElement(required = true)
    private Long id;

    @XmlElement(required = true)
    private UUID walletId;

    @XmlElement(required = true)
    private Long accountId;

    @XmlElement(required = true)
    private BigDecimal balance;

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime updateDate;

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime createDate;

    public WalletResponse() {
    }

    public WalletResponse(Long id, UUID walletId, Long accountId, BigDecimal balance, LocalDateTime updateDate, LocalDateTime createDate) {
        this.id = id;
        this.walletId = walletId;
        this.accountId = accountId;
        this.balance = balance;
        this.updateDate = updateDate;
        this.createDate = createDate;
    }


}
