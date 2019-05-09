package com.sap.cloud.rest.neo.api.client.integration;

class NeoApiClientIntegrationTest {

    static String getClientUser() {
        return System.getProperty("clientUser");
    }

    static String getSubaccount() {
        return System.getProperty("subaccount");
    }

    static String getRegionAlias() {
        return System.getProperty("regionAlias");
    }

    static String getProviderSubaccount() {
        return System.getProperty("providerSubaccount");
    }

    static String getProviderApplication() {
        return System.getProperty("providerApplication");
    }

    static String getPlatformClientId() {
        return System.getProperty("platformClientId");
    }

    static String getPlatformClientSecret() {
        return System.getProperty("platformClientSecret");
    }

}