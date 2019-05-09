package com.sap.cloud.rest.neo.api.client.config.host;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigApiHostProviderTest {

    private static final String REGION_ALIAS = "neo-eu1";
    private static final String CONFIG_API_HOST = "https://configapi.hana.ondemand.com";

    private ConfigApiHostProvider configApiHostProvider = new ConfigApiHostProvider();

    @Test
    public void getConfigApiHost_canary() {
        String configApiHost = configApiHostProvider.getConfigApiHost(REGION_ALIAS);
        assertEquals(CONFIG_API_HOST, configApiHost);
    }

}
