package it.disim.univaq.sose.search_service.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.disim.univaq.sose.search_service.domain.dto.*;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;

/**
 * The SearchService interface provides methods to search for a city or a location by latitude and longitude.
 */
@Path("/api/search")
public interface SearchService {

    /**
     * Search ads by specifying the title
     * @param searchByTitleRequest The request to search by title
     * @param asyncResponse The response to the search by title
     */
    @Operation(
            operationId = "searchByTitle",
            description = "Search ads by specifying the title",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Search performed",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = AdResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = AdResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Bad Requestss",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Service Unavailable",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ErrorResponse.class))
                            })
            }
    )
    @POST
    @Path("/searchByTitle")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    void searchByTitle(
            @RequestBody(
                    description = "SearchByTitle",
                    required = true,
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SearchByTitleRequest.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = SearchByTitleRequest.class))}
            ) SearchByTitleRequest searchByTitleRequest,
            @Suspended AsyncResponse asyncResponse
    );

    /**
     * Search ads by specifying the city name and optional criteria
     * @param paramSearchByCityRequest The request to search by city
     * @param paramAsyncResponse The response to the search by city
     */
    @Operation(
            operationId = "searchByCity",
            description = "Search ads by specifying the city name and optional criteria",
            responses = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "Search performed",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = AdResponse.class)),
                                @Content(mediaType = "application/xml", schema = @Schema(implementation = AdResponse.class))
                        }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "415",
                            description = "Bad Requestss",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Service Unavailable",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ErrorResponse.class))
                            })
            }
    )
    @POST
    @Path("/searchByCity")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    void searchByCity(
            @RequestBody(
                    description = "SearchByCIty",
                    required = true,
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SearchByCityRequest.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = SearchByCityRequest.class))}
            ) SearchByCityRequest paramSearchByCityRequest,
            @Suspended AsyncResponse paramAsyncResponse
    );

    @Operation(
            operationId = "searchByLatLong",
            description = "Search ads by specifying latitude, longitude and optional criteria",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Search performed",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = AdResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = AdResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Service Unavailable",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            })
            })
    @POST
    @Path("/searchByLatLong")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    void searchByLatLong(
            @RequestBody(
                    description = "SearchByLatLong",
                    required = true,
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = SearchByLatLongRequest.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = SearchByLatLongRequest.class))
                    }
            ) SearchByLatLongRequest paramSearchByLatLongRequest,
            @Suspended AsyncResponse paramAsyncResponse
    );

}