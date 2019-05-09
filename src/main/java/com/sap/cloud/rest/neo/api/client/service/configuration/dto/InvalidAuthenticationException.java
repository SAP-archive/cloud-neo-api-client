package com.sap.cloud.rest.neo.api.client.service.configuration.dto;

public class InvalidAuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidAuthenticationException(String message) {
        super(message);
    }
}
