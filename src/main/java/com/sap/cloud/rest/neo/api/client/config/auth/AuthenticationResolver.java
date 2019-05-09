package com.sap.cloud.rest.neo.api.client.config.auth;

import com.sap.cloud.rest.api.client.auth.Authentication;

/**
 * This class represents a resolver used to get the {@link Authentication} by
 * specified hostname.
 */
public interface AuthenticationResolver {

    /**
     * Returns the {@link Authentication} by specified hostname.
     * 
     * @param hostname
     *            - the hostname to get the {@link Authentication} for.
     * @return - the {@link Authentication} for the specified hostname.
     */
    Authentication getAuthentication(String hostname);
}