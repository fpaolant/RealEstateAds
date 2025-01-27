package it.disim.univaq.sose.ads_service.webservice;

public class ServiceUnavailableException extends Exception {
    private static final long serialVersionUID = -8195696378566893653L;

    public ServiceUnavailableException() {}

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(cause);
    }

    public ServiceUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}