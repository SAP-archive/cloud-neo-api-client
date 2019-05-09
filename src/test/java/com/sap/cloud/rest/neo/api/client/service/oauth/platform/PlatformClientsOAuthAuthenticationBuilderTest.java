package com.sap.cloud.rest.neo.api.client.service.oauth.platform;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sap.cloud.rest.api.client.auth.oauth.OAuthServerConfig;
import com.sap.cloud.rest.neo.api.client.region.PlatformDomains;

public class PlatformClientsOAuthAuthenticationBuilderTest {

    private static final String NEO_OAUTH_SERVER_HOST_PATTERN = "%s://oauthasservices-%s.%s";
    private static final String PROTOCOL = "https";
    private static final String REGION_ALIAS = "neo-eu1";
    private static final String PLATFORM_DOMAIN = new PlatformDomains().getPlatformDomain(REGION_ALIAS);
    private static final String SUBACCOUNT = "subaccount";
    private static final String CLIENT_ID = "clientId";
    private static final char[] CLIENT_SECRET = "clientSecret".toCharArray();

    @Test
    public void testBuildWithValidData() {
        OAuthServerConfig config = PlatformClientsOAuthAuthenticationBuilder.getBuilder()
                .subaccount(SUBACCOUNT)
                .regionAlias(REGION_ALIAS)
                .clientID(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .build()
                .getOAuthServerConfig();

        String formattedHost = String.format(NEO_OAUTH_SERVER_HOST_PATTERN, PROTOCOL, SUBACCOUNT, PLATFORM_DOMAIN);

        assertEquals(formattedHost, config.getOAuthServerHost());
        assertEquals(PlatformClientsOAuthAuthentication.NEO_OAUTH_SERVER_API_PATH, config.getoAuthServerApiPath());
        assertEquals(CLIENT_ID, config.getClientID());
        assertArrayEquals(CLIENT_SECRET, config.getClientSecret());
    }
}