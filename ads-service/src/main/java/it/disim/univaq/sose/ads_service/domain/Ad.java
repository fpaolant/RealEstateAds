package it.disim.univaq.sose.ads_service.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "ads")
public class Ad extends BaseEntity {
    private static final long serialVersionUID = 6021150229933872052L;

    @Column(name = "title", nullable = false)
    private String title;

    private String description;

    @Column(name = "square_meters", nullable = false)
    private Integer squareMeters;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "status")
    private AdStatus status = AdStatus.PENDING_APPROVAL;

    @Column(name = "approved")
    private boolean approved = false;

    @Column(name = "rejected")
    private boolean rejected = false;

    @Column(name = "rejection_reason")
    private String rejectionReason;

}