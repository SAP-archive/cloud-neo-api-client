package com.sap.cloud.rest.neo.api.client.service.configuration.exceptions;

import com.sap.cloud.rest.api.client.exceptions.ResponseException;
import com.sap.cloud.rest.api.client.model.HttpExchangeContext;

public class DestinationNotFoundException extends ResponseException {

    private static final long serialVersionUID = 1L;

    public DestinationNotFoundException(String message, HttpExchangeContext context) {
        super(message, context);
    }
}
