package it.disim.univaq.sose.search_service.domain.dto;


import it.disim.univaq.sose.ads_service.webservice.AdStatus;
import jakarta.jws.WebParam;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;


/**
 * The SearchByTitleRequest class is a DTO that represents the request to search ads by city.
 */
@Data
@XmlRootElement(name = "SearchByTitleRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchByTitleRequest {
    @XmlElement(required = true)
    private String title;

    private AdStatus status;

    private String sortBy;

    private String sortOrder;

    private int page;

    private int size;

    public SearchByTitleRequest() {}

    public SearchByTitleRequest(String title) {}

    public SearchByTitleRequest(String title, AdStatus status, String sortBy, String sortOrder, int page, int size) {
        this.title = title;
        this.status = status;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = page;
        this.size = size;
    }


}
