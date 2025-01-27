package it.disim.univaq.sose.account_service.service;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.disim.univaq.sose.account_service.domain.dto.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * The AccountService interface defines the service methods for the account service.
 */
@Path("/api/account")
public interface AccountService {
    /**
     * Authenticate user and return JWT
     *
     * @param userCredentials
     * @return
     */
    @Operation(
            operationId = "login",
            description = "Authenticate user and return JWT",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = TokenResponse.class))
                            }),
                    @ApiResponse(responseCode = "401", description = "Authentication failed",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @POST
    @Path("/login")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    Response login(@RequestBody(description = "Login", required = true, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserCredentials.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = UserCredentials.class))}) UserCredentials userCredentials);

    /**
     * Check JWT
     *
     * @param token
     * @return
     */
    @Operation(operationId = "checkTokenResponse", description = "Check JWT",
            responses = {
                @ApiResponse(responseCode = "200", description = "Check successful",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = Boolean.class))
                        }
                )
            }
    )
    @POST
    @Path("/check-token")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    Boolean checkTokenResponse(@RequestBody(description = "Token", required = true, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = TokenResponse.class))}) TokenResponse token);

    /**
     * Open Admin Account
     *
     * @param openAccountRequest
     * @return
     */
    @Operation(operationId = "openAccountAdmin", description = "openAccountAdmin",
            responses = {
                @ApiResponse(responseCode = "201", description = "Save Admin Account",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponse.class)),
                                @Content(mediaType = "application/xml", schema = @Schema(implementation =  AccountResponse.class))
                        },
                        headers = {@Header(name = "Location", description = "URL of the created resource", schema = @Schema(type = "string"))}
                ),
                @ApiResponse(responseCode = "500", description = "Account whit this Id not found",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                        }
               )
            }
    )
    @POST
    @Path("/admin-account")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    AccountResponse openAccountAdmin(@RequestBody(description = "Account to be saved", required = true, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OpenAccountRequest.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = OpenAccountRequest.class))}) OpenAccountRequest openAccountRequest);

    /**
     * Open User Account
     *
     * @param openAccountRequest
     * @return
     */
    @Operation(operationId = "openAccountUser", description = "openAccountUser",
            responses = {
                @ApiResponse(responseCode = "201", description = "Save User Account",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponse.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation =  AccountResponse.class))
                    },
                    headers = {
                            @Header(name = "Location", description = "URL of the created resource", schema = @Schema(type = "string"))
                    }),
                @ApiResponse(responseCode = "500", description = "Account whit this Id not found", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                        @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                })
            }
    )
    @POST
    @Path("/user-account")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    AccountResponse openAccountUser(@RequestBody(description = "Account to be saved", required = true, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OpenAccountRequest.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = OpenAccountRequest.class))}) OpenAccountRequest openAccountRequest);

    /**
     * Get Account
     *
     * @param paramLong
     * @return
     */
    @Operation(
            operationId = "getAccount",
            description = "Get Account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = AccountResponse.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Account not found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                        }
                    )
            })
    @GET
    @Path("/{id}")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    Response getAccount(@PathParam("id") long paramLong);

    /**
     * Get Accounts
     *
     * @param getAccountsRequest
     */
    @Operation(
            operationId = "getAccounts",
            description = "Get Accounts",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Accounts found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountPaginatedResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = AccountPaginatedResponse.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Accounts not found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }
                    )
            }
    )
    @POST
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    @Path("/accounts")
    AccountPaginatedResponse getAccounts(@RequestBody(
            description = "GetAccountsRequest",
            required = true,
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GetAccountsRequest.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = GetAccountsRequest.class))
            }
    ) GetAccountsRequest getAccountsRequest);

    /**
     * Promote User to Admin
     *
     * @param accountId
     */
    @Operation(
            operationId = "promoteAccount",
            description = "Promote Account to Admin",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "User promoted",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Response.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Account not found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }
                    )
            }
    )
    @PATCH
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    @Path("/{accountId}/promote")
    Response promoteAccount(@PathParam("accountId") long accountId);

    /**
     * Demote User to Admin
     *
     * @param accountId
     */
    @Operation(
            operationId = "demoteAccount",
            description = "Demote Account to User",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "User demoted",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Response.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Account not found",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)),
                                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponse.class))
                            }
                    )
            }
    )
    @PATCH
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    @Path("/{accountId}/demote")
    Response demoteAccount(@PathParam("accountId") long accountId);
}
