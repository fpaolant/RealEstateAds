package it.disim.univaq.sose.search_service.service;

import it.disim.univaq.sose.search_service.business.SearchManager;
import it.disim.univaq.sose.search_service.domain.dto.SearchByCityRequest;
import it.disim.univaq.sose.search_service.domain.dto.SearchByLatLongRequest;
import it.disim.univaq.sose.search_service.domain.dto.SearchByTitleRequest;
import jakarta.ws.rs.container.AsyncResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The SearchServiceImpl class is a service that provides methods to search for a city or a location by latitude and longitude.
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    private final SearchManager searchManager;

    public SearchServiceImpl(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    @Override
    public void searchByTitle(SearchByTitleRequest searchByTitleRequest, AsyncResponse asyncResponse) {
        this.searchManager.searchByTitle(searchByTitleRequest, asyncResponse);
    }

    public void searchByCity(SearchByCityRequest searchByCityRequest, AsyncResponse asyncResponse) {
        this.searchManager.searchByCity(searchByCityRequest, asyncResponse);
    }

    public void searchByLatLong(SearchByLatLongRequest searchByLatLongRequest, AsyncResponse asyncResponse) {
        this.searchManager.searchByLatLong(searchByLatLongRequest, asyncResponse);
    }
}
