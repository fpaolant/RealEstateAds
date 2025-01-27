package it.disim.univaq.sose.account_service.security;

import it.disim.univaq.sose.account_service.domain.Role;

/**
 * A record class representing the details of an account.
 *
 * @param username the username of the account
 * @param role     the role of the account
 */
public record AccountDetails(String username, Role role) {
}
