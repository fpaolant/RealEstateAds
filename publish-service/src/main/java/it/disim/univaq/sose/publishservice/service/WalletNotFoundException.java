package it.disim.univaq.sose.publishservice.service;

/**
 * The WalletNotFoundException class is a custom exception that extends the Exception class.
 */
public class WalletNotFoundException extends Exception {
    private static final long serialVersionUID = -686942604083975933L;

    public WalletNotFoundException() {}

    public WalletNotFoundException(String message) {
        super(message);
    }

    public WalletNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletNotFoundException(Throwable cause) {
        super(cause);
    }

    public WalletNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
