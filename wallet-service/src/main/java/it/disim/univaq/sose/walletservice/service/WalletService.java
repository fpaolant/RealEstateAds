package it.disim.univaq.sose.walletservice.service;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.disim.univaq.sose.walletservice.domain.dto.ChargeWalletRequest;
import it.disim.univaq.sose.walletservice.domain.dto.CreateWalletRequest;
import it.disim.univaq.sose.walletservice.domain.dto.WalletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.web.ErrorResponse;


import java.util.UUID;

/**
 * The WalletService interface defines the service methods for the account service.
 */
@Path("/api/wallet")
public interface WalletService {

    /**
     * Create a new Wallet
     *
     * @param createWalletRequest
     * @return
     */
    @Operation(
            operationId = "createWallet",
            description = "Create a new Wallet",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Wallet created",
                            headers = {
                                    @Header(name = "Location", description = "The URL to retrieve the created wallet", schema = @Schema(type = "string"))
                            },
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = WalletResponse.class))
                            }),
                    @ApiResponse(responseCode = "409", description = "Wallet already exists for the account",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }
                    )
            }
    )
    @POST
    @Path("/create")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Response createWallet(@RequestBody(description = "Wallet to be saved",
            required = true, content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CreateWalletRequest.class)),
            @Content(mediaType = "application/xml", schema = @Schema(implementation = CreateWalletRequest.class))}) CreateWalletRequest createWalletRequest);

    /**
     * Get Wallet Details
     *
     * @param walletId
     * @return
     */
    @Operation(
            operationId = "getWalletByWalletId",
            description = "Get Wallet Details by walletId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Wallet found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = WalletResponse.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Wallet not found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                        }
                    )
            })
    @GET
    @Path("/{walletId}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Response getWallet(@PathParam("walletId") UUID walletId);

    /**
     * Get Wallet Details
     *
     * @param accountId
     * @return
     */
    @Operation(
            operationId = "getWalletByAccountId",
            description = "Get Wallet Details by accountId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Wallet found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = WalletResponse.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Account Wallet not found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }
                    )
            })
    @GET
    @Path("/account/{accountId}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Response getWallet(@PathParam("accountId") long accountId);


    /**
     * Charge Wallet
     *
     * @param chargeWalletRequest
     * @return
     */
    @Operation(
            operationId = "chargeWallet",
            description = "Charge Wallet",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "ChargeWalletRequest",
                    required = true,
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ChargeWalletRequest.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ChargeWalletRequest.class))
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Wallet charged",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = WalletResponse.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Insufficient balance",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Wallet not found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                        }
                    )
            }
    )
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/charge")
    Response chargeWallet(ChargeWalletRequest chargeWalletRequest);

    /**
     * Recharge Wallet
     *
     * @param chargeWalletRequest
     * @return
     */
    @Operation(
            operationId = "reChargeWallet",
            description = "Recharge Wallet",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "ChargeWalletRequest",
                    required = true,
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ChargeWalletRequest.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ChargeWalletRequest.class))
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Wallet recharged",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = WalletResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = WalletResponse.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Wallet not found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }
                    )
            }
    )
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/recharge")
    Response reChargeWallet(ChargeWalletRequest chargeWalletRequest);

}
