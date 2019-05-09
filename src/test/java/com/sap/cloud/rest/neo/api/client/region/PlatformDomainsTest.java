package com.sap.cloud.rest.neo.api.client.region;

import static com.sap.cloud.rest.neo.api.client.region.PlatformDomains.REGION_ALIAS_CANNOT_BE_NULL_MSG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PlatformDomainsTest {

    private static final String DOMAIN_KEY = "neo-eu1";
    private static final String DOMAIN_HOST = "hana.ondemand.com";

    @Rule
    public ExpectedException expectedExceptions = ExpectedException.none();

    private static PlatformDomains platformDomains;

    @BeforeClass
    public static void setup() {
        platformDomains = new PlatformDomains();
    }

    @Test
    public void testNullRegionAlias() {
        expectedExceptions.expect(NullPointerException.class);
        expectedExceptions.expectMessage(REGION_ALIAS_CANNOT_BE_NULL_MSG);
        platformDomains.getPlatformDomain(null);
    }

    @Test
    public void testNotExistingRegionAlias() {
        String not_existing = platformDomains.getPlatformDomain("not_existing");
        assertNull(not_existing);
    }

    @Test
    public void getPlatformDomainTest() {
        String actualPlatformDomain = platformDomains.getPlatformDomain(DOMAIN_KEY);
        assertEquals(DOMAIN_HOST, actualPlatformDomain);
    }
}