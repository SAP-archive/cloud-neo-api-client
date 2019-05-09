package com.sap.cloud.rest.neo.api.client.config.host;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AuthorizationManagementHostProviderTest {

    private AuthorizationManagementHostProvider authorizationManagementHostProvider = new AuthorizationManagementHostProvider();

    private static final String SUBACCOUNT_NAME = "olrvnah5h0";

    private static final String REGION_ALIAS_CANARY = "neo-eu1";
    private static final String APITUNNEL_CERT_CANARY_HOST = "https://api.hana.ondemand.com";

    @Test
    public void getApitunnelHost_canary() {
        String apitunnelHost = authorizationManagementHostProvider.getAuthorizationManagementHost(REGION_ALIAS_CANARY,
                SUBACCOUNT_NAME);
        assertEquals(APITUNNEL_CERT_CANARY_HOST, apitunnelHost);
    }

}
