package it.disim.univaq.sose.search_service.business;


import it.disim.univaq.sose.search_service.domain.dto.SearchByCityRequest;
import it.disim.univaq.sose.search_service.domain.dto.SearchByLatLongRequest;
import it.disim.univaq.sose.search_service.domain.dto.SearchByTitleRequest;
import jakarta.ws.rs.container.AsyncResponse;

/**
 * The SearchManager interface provides methods to search for a city or a location by latitude and longitude.
 */
public interface SearchManager {
    /**
     * Search ads by title.
     *
     * @param searchByTitleRequest the search by title request
     * @param asyncResponse the param async response
     */
    void searchByTitle(SearchByTitleRequest searchByTitleRequest, AsyncResponse asyncResponse);

    /**
     * Search ads by city.
     *
     * @param searchByCityRequest the search by city request
     * @param asyncResponse the param async response
     */
    void searchByCity(SearchByCityRequest searchByCityRequest, AsyncResponse asyncResponse);

    /**
     * Search ads by latitude and longitude.
     *
     * @param searchByLatLongRequest the search by latitude and longitude request
     * @param asyncResponse the param async response
     */
    void searchByLatLong(SearchByLatLongRequest searchByLatLongRequest, AsyncResponse asyncResponse);
}
