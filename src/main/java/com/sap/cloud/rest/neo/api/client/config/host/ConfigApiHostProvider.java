package com.sap.cloud.rest.neo.api.client.config.host;

import com.sap.cloud.rest.neo.api.client.region.PlatformDomains;

public class ConfigApiHostProvider {

    private static final String CONFIG_API_HOST_PATTERN = "https://configapi.%s";

    private PlatformDomains platformDomains;

    public ConfigApiHostProvider() {
        platformDomains = new PlatformDomains();
    }

    /**
     * Provides the host for the Neo Configuration Service API by specified
     * region alias.
     * 
     * @param regionAlias
     *            - the region alias to provide the host for.
     * @return - the host for the Neo Configuration Service.
     */
    public String getConfigApiHost(String regionAlias) {
        return String.format(CONFIG_API_HOST_PATTERN, platformDomains.getPlatformDomain(regionAlias));
    }

}
