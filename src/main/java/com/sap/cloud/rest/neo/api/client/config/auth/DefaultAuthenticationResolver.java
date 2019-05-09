package com.sap.cloud.rest.neo.api.client.config.auth;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.sap.cloud.rest.api.client.auth.Authentication;

public class DefaultAuthenticationResolver implements AuthenticationResolver {

    private Map<String, Authentication> authenticationMappings;

    public DefaultAuthenticationResolver(Map<String, Authentication> authenticationMappings) {
        this.authenticationMappings = authenticationMappings;
    }

    /**
     * Returns the {@link Authentication} for the first match of the pattern
     * provided in the {@link Map<String, Authentication>} and the specified
     * hostname.
     * 
     * @param hostname
     *            - the hostname to get the {@link Authentication} for.
     * @return - the {@link Authentication} for the specified hostname or null
     *         if no {@link Authentication} matches the specified hostname.
     */
    @Override
    public Authentication getAuthentication(String hostname) {
        for (Entry<String, Authentication> authenticationMapping : authenticationMappings.entrySet()) {
            if (matches(authenticationMapping.getKey(), hostname)) {
                return authenticationMapping.getValue();
            }
        }
        return null;
    }

    private boolean matches(String pattern, String hostname) {
        return Pattern.compile(pattern)
                .matcher(hostname)
                .matches();
    }
}