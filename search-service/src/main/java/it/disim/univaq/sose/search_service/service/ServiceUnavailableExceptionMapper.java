package it.disim.univaq.sose.search_service.service;


import it.disim.univaq.sose.search_service.domain.dto.ErrorResponse;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class ServiceUnavailableExceptionMapper implements ExceptionMapper<ServiceUnavailableException> {
    @Context
    private HttpHeaders headers;

    public Response toResponse(ServiceUnavailableException exception) {
        MediaType responseType = determineResponseType();
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(new ErrorResponse(exception.getMessage()))
                .type(responseType)
                .build();
    }

    private MediaType determineResponseType() {
        List<MediaType> acceptableMediaTypes = this.headers.getAcceptableMediaTypes();
        if (acceptableMediaTypes.contains(MediaType.APPLICATION_JSON_TYPE))
            return MediaType.APPLICATION_JSON_TYPE;
        if (acceptableMediaTypes.contains(MediaType.APPLICATION_XML_TYPE))
            return MediaType.APPLICATION_XML_TYPE;
        return MediaType.TEXT_PLAIN_TYPE;
    }
}
