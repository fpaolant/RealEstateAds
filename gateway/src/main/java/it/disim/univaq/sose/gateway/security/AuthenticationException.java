package it.disim.univaq.sose.gateway.security;

import java.io.Serial;

/**
 * The AuthenticationException class is an exception that represents an error during the authentication process.
 */
public class AuthenticationException extends Exception {
    @Serial
    private static final long serialVersionUID = -8901548288926489495L;

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
