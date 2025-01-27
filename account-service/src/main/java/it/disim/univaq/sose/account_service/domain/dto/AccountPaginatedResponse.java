package it.disim.univaq.sose.account_service.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.util.List;

/**
 * The AccountPaginatedResponse class is a DTO that represents the response to a paginated request.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountPaginatedResponse", propOrder = {"content", "currentPage", "totalPages", "totalItems"})
public class AccountPaginatedResponse {
    @XmlElement(name = "adResponse")
    private List<AccountResponse> content;

    private int currentPage;

    private int totalPages;

    private long totalItems;



    public AccountPaginatedResponse() {}

    public AccountPaginatedResponse(List<AccountResponse> content, int totalPages, long totalItems) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public AccountPaginatedResponse(List<AccountResponse> content, int currentPage, int totalPages, long totalItems) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }
}