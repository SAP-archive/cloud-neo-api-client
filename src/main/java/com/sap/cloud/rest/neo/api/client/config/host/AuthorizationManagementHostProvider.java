package com.sap.cloud.rest.neo.api.client.config.host;

import com.sap.cloud.rest.neo.api.client.region.PlatformDomains;

public class AuthorizationManagementHostProvider {

    private static final String API_HOST_PATTERN = "https://api.%s";

    private final PlatformDomains platformDomains;

    public AuthorizationManagementHostProvider() {
        platformDomains = new PlatformDomains();
    }

    public String getAuthorizationManagementHost(String regionAlias, String subaccount) {
        String platformDomain = platformDomains.getPlatformDomain(regionAlias);
        return String.format(API_HOST_PATTERN, platformDomain);
    }

}
