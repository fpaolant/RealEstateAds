package it.disim.univaq.sose.publishservice.domain.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.util.List;

/**
 * The PaginatedResponse class is a DTO that represents the response to a paginated request.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaginatedResponse", propOrder = {"content", "currentPage", "totalPages", "totalItems"})
public class AdPaginatedResponse {
    @XmlElement(name = "adResponse")
    private List<AdResponse> content;

    private int currentPage;

    private int totalPages;

    private long totalItems;



    public AdPaginatedResponse() {}

    public AdPaginatedResponse(List<AdResponse> content, int totalPages, long totalItems) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public String toString() {
        return "AdPaginatedResponse(content=" + getContent() + ", currentPage=" + getCurrentPage() + ", totalPages=" + getTotalPages() + ", totalItems=" + getTotalItems() + ")";
    }
}