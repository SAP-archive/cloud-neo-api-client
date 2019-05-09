package com.sap.cloud.rest.neo.api.client.region;

import static java.util.Objects.requireNonNull;

import com.sap.cloud.rest.neo.api.client.property.PropertiesLoader;

public class PlatformDomains {

    static final String REGION_ALIAS_CANNOT_BE_NULL_MSG = "regionAlias cannot be null.";

    private static final String PLATFORM_DOMAINS_PROPERTIES_PATH = "platform-domains.properties";

    private PropertiesLoader propertiesLoader;

    public PlatformDomains() {
        propertiesLoader = new PropertiesLoader(PLATFORM_DOMAINS_PROPERTIES_PATH);
    }

    public String getPlatformDomain(String regionAlias) {
        requireNonNull(regionAlias, REGION_ALIAS_CANNOT_BE_NULL_MSG);
        return propertiesLoader.getProperty(regionAlias);
    }

}