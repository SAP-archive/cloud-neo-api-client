package com.sap.cloud.rest.neo.api.client.service.oauth.platform;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sap.cloud.rest.api.client.auth.oauth.OAuthServerConfig;
import com.sap.cloud.rest.neo.api.client.region.PlatformDomains;

public class PlatformClientsOAuthAuthenticationTest {

    private static final String REGION_ALIAS = "neo-eu1";
    private static final String SUBACCOUNT = "subaccount";
    private static final String CLIENT_ID = "clientId";
    private static final char[] CLIENT_SECRET = "clientSecret".toCharArray();

    private static final String NEO_OAUTH_SERVER_API_PATH = "/oauth2/api/v1/apitoken";
    private static final String NEO_OAUTH_SERVER_HOST = "https://oauthasservices-" + SUBACCOUNT + "."
            + new PlatformDomains().getPlatformDomain(REGION_ALIAS);

    @Test
    public void configTest_canary() {
        OAuthServerConfig oAuthServerConfig = new PlatformClientsOAuthAuthentication(REGION_ALIAS,
                SUBACCOUNT, CLIENT_ID, CLIENT_SECRET).getOAuthServerConfig();

        assertEquals(NEO_OAUTH_SERVER_HOST, oAuthServerConfig.getOAuthServerHost());
        assertEquals(NEO_OAUTH_SERVER_API_PATH, oAuthServerConfig.getoAuthServerApiPath());
        assertEquals(CLIENT_ID, oAuthServerConfig.getClientID());
        assertArrayEquals(CLIENT_SECRET, oAuthServerConfig.getClientSecret());

    }
}