package it.disim.univaq.sose.walletservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "wallet")
public class Wallet extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -4359230412570045144L;

    @Column(nullable = false, unique = true)
    private UUID walletId;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private BigDecimal balance;



    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy
                ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : Objects.hash(walletId, accountId, balance);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Wallet wallet = (Wallet) obj;
        return Objects.equals(walletId, wallet.walletId) && Objects.equals(accountId, wallet.accountId) && Objects.equals(balance, wallet.balance);
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }
}
