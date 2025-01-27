package it.disim.univaq.sose.account_service.security;

/**
 * An exception thrown when an authentication error occurs.
 */
public class AuthenticationException extends Exception {
    private static final long serialVersionUID = -7574209823462477590L;

    public AuthenticationException() {}

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    protected AuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
