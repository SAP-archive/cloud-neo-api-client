package com.sap.cloud.rest.neo.api.client.service.oauth.platform;

import com.sap.cloud.rest.api.client.auth.oauth.OAuthAuthentication;
import com.sap.cloud.rest.neo.api.client.service.oauth.NeoOAuthAuthentication;

/**
 * This class represents an implementation of the {@link OAuthAuthentication}
 * and adds public neo platform clients specific configuration.
 */
public class PlatformClientsOAuthAuthentication extends NeoOAuthAuthentication {

    static final String NEO_OAUTH_SERVER_API_PATH = "/oauth2/api/v1/apitoken";

    public PlatformClientsOAuthAuthentication(String regionAlias, String subaccount,
            String clientID, char[] clientSecret) {
        super(regionAlias, subaccount, clientID, clientSecret, NEO_OAUTH_SERVER_API_PATH);
    }

}