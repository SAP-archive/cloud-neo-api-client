package com.sap.cloud.rest.neo.api.client.service.oauth;

import static java.lang.String.format;

import com.sap.cloud.rest.api.client.auth.oauth.OAuthAuthentication;
import com.sap.cloud.rest.api.client.auth.oauth.OAuthServerConfigBuilder;
import com.sap.cloud.rest.neo.api.client.region.PlatformDomains;

public abstract class NeoOAuthAuthentication extends OAuthAuthentication {

    private static final String HTTPS_PROTOCOL = "https";
    private static final String NEO_OAUTH_SERVER_HOST_PATTERN = "%s://oauthasservices-%s.%s";

    private static PlatformDomains platformDomains = new PlatformDomains();

    protected NeoOAuthAuthentication(String regionAlias, String subaccount,
            String clientID, char[] clientSecret, String oauthServerApiPath) {
        super(OAuthServerConfigBuilder.getBuilder()
                .oAuthServerHost(getOAuthServerHost(subaccount, regionAlias))
                .oAuthServerApiPath(oauthServerApiPath)
                .clientID(clientID)
                .clientSecret(clientSecret)
                .build());
    }

    private static String getOAuthServerHost(String subaccount, String regionAlias) {
        String protocol = HTTPS_PROTOCOL;
        return format(NEO_OAUTH_SERVER_HOST_PATTERN, protocol, subaccount,
                platformDomains.getPlatformDomain(regionAlias));
    }

}
