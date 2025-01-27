package it.disim.univaq.sose.ads_service.webservice;

public class AccountServiceException extends Exception {
    private static final long serialVersionUID = -6744391794487557824L;

    public AccountServiceException() {}

    public AccountServiceException(String message) {
        super(message);
    }

    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountServiceException(Throwable cause) {
        super(cause);
    }

    public AccountServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}