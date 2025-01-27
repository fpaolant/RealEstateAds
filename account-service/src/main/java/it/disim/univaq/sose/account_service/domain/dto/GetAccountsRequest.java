package it.disim.univaq.sose.account_service.domain.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

/**
 * The GetAccountsRequest class is a DTO that represents the request to publish an ad.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAccountsRequest", propOrder = {})
public class GetAccountsRequest {
    @XmlElement(required = true)
    private int page;

    @XmlElement(required = true)
    private int size;

    private String sortBy;

    private String sortOrder;

    public GetAccountsRequest() {
    }

    public GetAccountsRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public GetAccountsRequest(int page, int size, String sortBy, String sortOrder) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    public String toString() {
        return "GetAccountsRequest(page=" + getPage() + ", size=" + getSize() + ")";
    }

}