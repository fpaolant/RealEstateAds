package it.disim.univaq.sose.search_service.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import it.disim.univaq.sose.ads_service.webservice.AdStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The SearchByLatLongRequest class is a DTO that represents the request to search ads by latitude and longitude.
 */
@Data
@Schema(description = "Request to search ads by latitude and longitude")
public class SearchByLatLongRequest {
    @Min(value = -90L, message = "Latitude must be between -90 and 90")
    @Max(90L)
    @NotNull
    @Schema(description = "Latitude of starting point", example = "41.9028", required = true)
    private double latitude;

    @Min(value = -180L, message = "Longitude must be between -180 and 180")
    @Max(180L)
    @NotNull
    @Schema(description = "Longitude of starting point", example = "12.4964", required = true)
    private double longitude;

    @Min(value = 1L, message = "Radius must be non-negative")
    @Max(value = 500L, message = "Radius must be less than 500")
    @NotNull
    @Schema(description = "Radius of search in km", example = "20", required = true)
    private int radius;

    @Min(value = 1L, message = "Price must be greater than 1000")
    @Max(value = 100000000L, message = "Price must be less than 100000000")
    @Schema(description = "MaxPrice", example = "200000", required = false)
    private BigDecimal maxPrice;

    private AdStatus status;

    private String sortBy;

    private String sortOrder;

    private int page;

    private int size;


    public SearchByLatLongRequest() {}

    public SearchByLatLongRequest(double latitude, double longitude, int radius, BigDecimal maxPrice) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.maxPrice = maxPrice;
    }

    public SearchByLatLongRequest(double latitude, double longitude, int radius, BigDecimal maxPrice,
                                  AdStatus status, String sortBy, String sortOrder, int page, int size) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.maxPrice = maxPrice;
        this.status = status;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = page;
        this.size = size;
    }


}
