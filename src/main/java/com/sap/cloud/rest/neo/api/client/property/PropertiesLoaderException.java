package com.sap.cloud.rest.neo.api.client.property;

public class PropertiesLoaderException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PropertiesLoaderException(String message, Exception e) {
        super(message, e);
    }
}
