package it.disim.univaq.sose.publishservice.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.disim.univaq.sose.publishservice.domain.dto.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;


/**
 * The PublishService interface defines the service methods for the publish service.
 */
@Path("/api/publish")
public interface PublishService {
    /**
     * Publish an ad.
     *
     * @param ad the ad to publish
     * @param asyncResponse the async response
     * @return the response
     */
    @Operation(
            operationId = "publishAd",
            description = "Publish an ad.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ad published successfully",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = PublishAdResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = PublishAdResponse.class))
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
    @Path("/ad")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    void publishAd(@RequestBody(
            description = "PublishAdRequest",
            required = true,
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PublishAdRequest.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = PublishAdRequest.class))}
    ) PublishAdRequest ad, @Suspended AsyncResponse asyncResponse);


    /**
     * Approve an ad.
     *
     * @param adId the ad ID
     * @param asyncResponse the async response
     * @return the response
     */
    @Operation(
            operationId = "approveAd",
            description = "Approve an ad.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ad approved successfully",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = PublishAdApproveResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = PublishAdApproveResponse.class))
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
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            })
            }
    )
    @PUT
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    @Path("/ad/{adId}/approve")
    void approveAd(@PathParam("adId") long adId , @Suspended AsyncResponse asyncResponse);



    /**
     * Reject an ad.
     *
     * @param adId the ad ID
     * @param asyncResponse the async response
     * @param rejectRequest the reject request
     * @return the response
     */
    @Operation(
            operationId = "rejectAd",
            description = "Reject an ad.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ad rejected successfully",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = PublishAdRejectResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = PublishAdRejectResponse.class))
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
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            })
            }
    )
    @PUT
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    @Path("/ad/{adId}/reject")
    void rejectAd(@PathParam("adId") long adId,
                  @RequestBody(
            description = "Reason for rejection",
            required = true,
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PublishAdRejectRequest.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = PublishAdRejectRequest.class))}
    ) PublishAdRejectRequest rejectRequest, @Suspended AsyncResponse asyncResponse);

    /**
     * Get all ads.
     *
     * @param asyncResponse the async response
     * @return the response
     */
    @Operation(
            operationId = "getAds",
            description = "Get all ads.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ads retrieved successfully",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = AdPaginatedResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = AdPaginatedResponse.class))
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
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            })
            }
    )
    @POST
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    @Path("/ads")
    void getAds(@RequestBody(
            description = "GetAdsRequest",
            required = true,
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GetAdsRequest.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = GetAdsRequest.class))
            }
    ) GetAdsRequest getAdsRequest, @Suspended AsyncResponse asyncResponse);


    /**
     * Get all ads by account ID.
     *
     * @param accountId the account ID
     * @param asyncResponse the async response
     * @return the response
     */
    @Operation(
            operationId = "getAdsByAccountId",
            description = "Get all ads by account ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ads retrieved successfully",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = PublishGetAdsResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = PublishGetAdsResponse.class))
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
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            })
            }
    )
    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/ads/account/{accountId}")
    void getAdsByAccountId(@PathParam("accountId") long accountId, @Suspended AsyncResponse asyncResponse);


    /**
     * Open a publish account.
     *
     * @param openPublishAccountRequest the open publish account request
     * @param asyncResponse the async response
     * @return the response
     */
    @Operation(
            operationId = "openPublishAccount",
            description = "Open a publish account.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account opened successfully",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = OpenPublishAccountResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = OpenPublishAccountResponse.class))
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
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            })
            }
    )
    @POST
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    @Path("/account")
    void openPublishAccount(@RequestBody(
            description = "OpenPublishAccountRequest",
            required = true,
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OpenPublishAccountRequest.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = OpenPublishAccountRequest.class))
            }
    ) OpenPublishAccountRequest openPublishAccountRequest, @Suspended AsyncResponse asyncResponse);
}
