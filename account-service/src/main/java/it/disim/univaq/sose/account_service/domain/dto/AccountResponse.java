package it.disim.univaq.sose.account_service.domain.dto;

import it.disim.univaq.sose.account_service.domain.Role;
import it.disim.univaq.sose.account_service.domain.dto.adapter.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@XmlRootElement(name = "AccountResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountResponse implements Serializable {
    private static final long serialVersionUID = 4592896323731902686L;

    @XmlElement(required = true)
    private Long id;

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private String surname;

    @XmlElement(required = true)
    private String username;

    @XmlElement(required = true)
    private String email;

    @XmlElement(required = true)
    private String mobile;

    @XmlElement(required = true)
    private Role role;

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime updateDate;

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime createDate;


    public AccountResponse() {}

    public AccountResponse(Long id, String name, String surname, String username, String email, String mobile, Role role, LocalDateTime updateDate, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
        this.updateDate = updateDate;
        this.createDate = createDate;
    }
}
