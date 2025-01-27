package it.disim.univaq.sose.ads_service.webservice;

public class AdException extends Exception {
    private static final long serialVersionUID = 3681684414781281457L;

    public AdException(String message) {
        super(message);
    }

    public AdException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdException(Throwable cause) {
        super(cause);
    }

    public AdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

