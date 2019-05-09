package com.sap.cloud.rest.neo.api.client.service.configuration;

import static org.slf4j.helpers.MessageFormatter.arrayFormat;
import static org.slf4j.helpers.MessageFormatter.format;

import org.apache.http.HttpStatus;

import com.sap.cloud.rest.api.client.exceptions.UnauthorizedException;
import com.sap.cloud.rest.api.client.handler.DefaultStatusCodeHandler;
import com.sap.cloud.rest.neo.api.client.service.configuration.exceptions.DestinationNotFoundException;
import com.sap.cloud.rest.neo.api.client.service.configuration.exceptions.PermissionDeniedException;

public class DestinationConfigurationServiceStatusCodeHandler extends DefaultStatusCodeHandler {

    static final String UNAUTHORIZED_MSG = "Request returned Unauthorized. Check if there is a valid token, or if the subaccount [{}] exists. Context: [{}]";
    static final String SUBSCRIPTION_UNAUTHORIZED_MSG = "Request returned Unauthorized. Check if there is a valid token, or if the consumer subaccount [{}] and/or "
            + "the provider subaccount [{}] exist and whether the provider subaccount [{}] is subscribed to application [{}]. Context: [{}]";
    static final String PERMISSION_DENIED_MSG = "Request returned Permission Denied. Requred scope [{}] is missing. Context: [{}]";
    static final String DESTINATION_NOT_FOUND_MSG = "Destination with name [{}] for application [{}] in subaccount [{}] was not found. Context: [{}]";
    static final String DESTINATION_NOT_FOUND_ACCOUNT_LEVEL_MSG = "Destination with name [{}] for subaccount [{}] was not found. Context: [{}]";

    private DestinationConfigurationServiceStatusCodeHandler() {
        //prevent initialization from constructor
    }

    public static DestinationConfigurationServiceStatusCodeHandler create(String subaccountId,
            String destinationName, String requiredScope) {
        return (DestinationConfigurationServiceStatusCodeHandler) new DestinationConfigurationServiceStatusCodeHandler()
                .addHandler(HttpStatus.SC_UNAUTHORIZED,
                        unauthorizedHandler(subaccountId))
                .addHandler(HttpStatus.SC_FORBIDDEN, forbiddenHandler(requiredScope))
                .addHandler(HttpStatus.SC_NOT_FOUND, notFoundHandler(subaccountId, destinationName));
    }

    public static DestinationConfigurationServiceStatusCodeHandler create(String subaccountId,
            String applicationName, String destinationName, String requiredScope) {
        return (DestinationConfigurationServiceStatusCodeHandler) new DestinationConfigurationServiceStatusCodeHandler()
                .addHandler(HttpStatus.SC_UNAUTHORIZED,
                        unauthorizedHandler(subaccountId))
                .addHandler(HttpStatus.SC_FORBIDDEN, forbiddenHandler(requiredScope))
                .addHandler(HttpStatus.SC_NOT_FOUND, notFoundHandler(subaccountId, applicationName, destinationName));
    }

    public static DestinationConfigurationServiceStatusCodeHandler create(String subaccountId,
            String providerSubaccountId, String applicationName,
            String destinationName, String requiredScope) {
        return (DestinationConfigurationServiceStatusCodeHandler) new DestinationConfigurationServiceStatusCodeHandler()
                .addHandler(HttpStatus.SC_UNAUTHORIZED,
                        unauthorizedHandler(subaccountId, providerSubaccountId, applicationName))
                .addHandler(HttpStatus.SC_FORBIDDEN, forbiddenHandler(requiredScope))
                .addHandler(HttpStatus.SC_NOT_FOUND, notFoundHandler(subaccountId, applicationName, destinationName));
    }

    private static ContextHandler unauthorizedHandler(String subaccountId) {
        return context -> {
            throw new UnauthorizedException(format(UNAUTHORIZED_MSG, subaccountId, context).getMessage(),
                    context);
        };
    }

    private static ContextHandler unauthorizedHandler(String subaccountId, String providerSubaccountId,
            String applicationName) {
        return context -> {
            throw new UnauthorizedException(
                    arrayFormat(SUBSCRIPTION_UNAUTHORIZED_MSG,
                            new Object[] { subaccountId, providerSubaccountId, providerSubaccountId,
                                    applicationName, context }).getMessage(),
                    context);
        };
    }

    private static ContextHandler forbiddenHandler(String requiredScope) {
        return context -> {
            throw new PermissionDeniedException(
                    format(PERMISSION_DENIED_MSG, requiredScope, context).getMessage(),
                    context);
        };
    }

    private static ContextHandler notFoundHandler(String subaccountId, String destinationName) {
        return context -> {
            throw new DestinationNotFoundException(
                    arrayFormat(DESTINATION_NOT_FOUND_ACCOUNT_LEVEL_MSG,
                            new Object[] { destinationName, subaccountId, context }).getMessage(),
                    context);
        };
    }

    private static ContextHandler notFoundHandler(String subaccountId, String applicationName,
            String destinationName) {
        return context -> {
            throw new DestinationNotFoundException(
                    arrayFormat(DESTINATION_NOT_FOUND_MSG,
                            new Object[] { destinationName, applicationName, subaccountId, context }).getMessage(),
                    context);
        };
    }
}
