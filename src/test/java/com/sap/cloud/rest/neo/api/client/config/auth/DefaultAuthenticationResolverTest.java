package com.sap.cloud.rest.neo.api.client.config.auth;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.sap.cloud.rest.api.client.auth.Authentication;
import com.sap.cloud.rest.api.client.auth.none.NoAuthentication;

public class DefaultAuthenticationResolverTest {

    private static final String MATCHING_HOSTNAME = "https://matching.hana.ondemand.com";
    private static final String NOT_MATCHING_HOSTNAME = "https://none.hana.ondemand.com";

    private AuthenticationResolver authenticationResolver;

    @Before
    public void setup() {
        Map<String, Authentication> authMappings = new HashMap<>();
        authMappings.put(".*matching.*", new NoAuthentication());
        authenticationResolver = new DefaultAuthenticationResolver(authMappings);
    }

    @Test
    public void getAuthentication_matched() {
        Authentication authentication = authenticationResolver.getAuthentication(MATCHING_HOSTNAME);
        assertNotNull(authentication);
    }

    @Test
    public void getAuthentication_notMatched() {
        Authentication authentication = authenticationResolver.getAuthentication(NOT_MATCHING_HOSTNAME);
        assertNull(authentication);
    }
}