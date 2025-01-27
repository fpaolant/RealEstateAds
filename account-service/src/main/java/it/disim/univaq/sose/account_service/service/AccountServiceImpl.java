package it.disim.univaq.sose.account_service.service;


import it.disim.univaq.sose.account_service.business.AccountManager;
import it.disim.univaq.sose.account_service.domain.dto.*;
import it.disim.univaq.sose.account_service.security.AuthenticationException;

import jakarta.ws.rs.core.Response;
import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The AccountServiceImpl class is the implementation of the AccountService interface.
 */
@Service
@Features(features = {"org.apache.cxf.ext.logging.LoggingFeature"})
public class AccountServiceImpl implements AccountService {
    private final AccountManager accountManager;

    @Value("${cxf.path}")
    private String cxfPath;

    public AccountServiceImpl(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * {@inheritDoc}
     */
    public Response login(UserCredentials userCredentials) {
        try {
            String token = this.accountManager.getJwtToken(userCredentials);
            return Response.ok(new TokenResponse(token)).build();
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    /**
     * {@inheritDoc}
     */
    public Boolean checkTokenResponse(TokenResponse token) {
        return this.accountManager.checkJwtToken(token.getToken());
    }

    /**
     * {@inheritDoc}
     */
    public AccountResponse openAccountAdmin(OpenAccountRequest request) {
        return this.accountManager.createAccountAdmin(request);
    }

    /**
     * {@inheritDoc}
     */
    public AccountResponse openAccountUser(OpenAccountRequest request) {
        return this.accountManager.createAccountUser(request);
    }

    /**
     * {@inheritDoc}
     */
    public Response getAccount(long id) {
        try {
            return Response.ok(this.accountManager.getAccountByIdAccount(Long.valueOf(id))).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountPaginatedResponse getAccounts(GetAccountsRequest getAccountsRequest) {
        return this.accountManager.getAccounts(getAccountsRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response promoteAccount(long accountId) {
        try {
            this.accountManager.promoteUser(accountId);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response demoteAccount(long accountId) {
        try {
            this.accountManager.demoteUser(accountId);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse(e.getMessage())).build();
        }
    }

}