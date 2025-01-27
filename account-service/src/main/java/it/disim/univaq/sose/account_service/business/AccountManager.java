package it.disim.univaq.sose.account_service.business;

import it.disim.univaq.sose.account_service.domain.dto.*;
import it.disim.univaq.sose.account_service.security.AuthenticationException;
import it.disim.univaq.sose.account_service.service.NotFoundException;

public interface AccountManager {
    /**
     * Generates a JWT token for the given user credentials.
     *
     * @param paramUserCredentials the user credentials
     * @return the JWT token
     * @throws AuthenticationException if authentication fails
     */
    String getJwtToken(UserCredentials paramUserCredentials) throws AuthenticationException;

    /**
     * Checks if the given JWT token is valid.
     *
     * @param paramString the JWT token
     * @return true if the token is valid, false otherwise
     */
    Boolean checkJwtToken(String paramString);

    /**
     * Creates a new user account.
     *
     * @param paramOpenAccountRequest the account request
     * @return the account response
     */
    AccountResponse createAccountUser(OpenAccountRequest paramOpenAccountRequest);

    /**
     * Creates a new admin account.
     *
     * @param paramOpenAccountRequest the account request
     * @return the account response
     */
    AccountResponse createAccountAdmin(OpenAccountRequest paramOpenAccountRequest);

    /**
     * Retrieves an account by its ID.
     *
     * @param paramLong the account ID
     * @return the account response
     * @throws NotFoundException if the account is not found
     */
    AccountResponse getAccountByIdAccount(Long paramLong) throws NotFoundException;

    /**
     * Retrieves a list of accounts.
     *
     * @param getAccountsRequest the request
     * @return the response
     */
    AccountPaginatedResponse getAccounts(GetAccountsRequest getAccountsRequest);

    /**
     * Promotes a user to admin.
     *
     * @param accountId the account ID
     */
    void promoteUser(long accountId) throws NotFoundException;

    /**
     * Demotes an admin to user.
     *
     * @param accountId the account ID
     */
    void demoteUser(long accountId) throws NotFoundException;
}
