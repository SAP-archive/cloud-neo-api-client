package com.sap.cloud.rest.neo.api.client.config;

import static org.junit.Assert.assertNotNull;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.sap.cloud.rest.api.client.auth.Authentication;
import com.sap.cloud.rest.api.client.auth.basic.BasicAuthentication;
import com.sap.cloud.rest.api.client.auth.oauth.OAuthAuthentication;
import com.sap.cloud.rest.api.client.auth.oauth.OAuthServerConfig;
import com.sap.cloud.rest.neo.api.client.config.auth.AuthenticationResolver;
import com.sap.cloud.rest.neo.api.client.config.auth.DefaultAuthenticationResolver;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.AuthorizationManagementService;
import com.sap.cloud.rest.neo.api.client.service.configuration.DestinationConfigurationService;

public class NeoClientFactoryTest {

    private static final String REGION_ALIAS = "neo-eu1";
    private static final String SUBACCOUNT = "subaccount-id";

    private static final String OAUTH_SERVER_HOST = "https://authentication.hana.ondemand.com";
    private static final String CLIENT_ID = "clientId";
    private static final char[] CLIENT_SECRET = "clientSecret".toCharArray();
    private static final OAuthServerConfig OAUTH_CONFIG = new OAuthServerConfig(OAUTH_SERVER_HOST, CLIENT_ID,
            CLIENT_SECRET);
    private static final Authentication OAUTH_AUTHENTICATION = new OAuthAuthentication(OAUTH_CONFIG);

    private static final String USERNAME = "username";
    private static final char[] PASSWORD = "password".toCharArray();
    private static final Authentication BASIC_AUTHENTICATION = new BasicAuthentication(USERNAME, PASSWORD);

    private static final Entry<String, Authentication> CONFIG_API_AUTH_MAPPING = new SimpleEntry<>(
            "https://configapi\\..*", OAUTH_AUTHENTICATION);

    private static final Entry<String, Authentication> AUTH_MANAGEMENT_API_AUTH_MAPPING = new SimpleEntry<>(
            "https://api\\..*", BASIC_AUTHENTICATION);

    private NeoClientFactory neoClientFactory;
    private NeoClientFactory neoClientFactoryWithResolver;

    @Before
    public void setup() {
        neoClientFactory = new NeoClientFactory(OAUTH_AUTHENTICATION);

        Map<String, Authentication> authMappings = new HashMap<>();
        authMappings.put(CONFIG_API_AUTH_MAPPING.getKey(), CONFIG_API_AUTH_MAPPING.getValue());
        authMappings.put(AUTH_MANAGEMENT_API_AUTH_MAPPING.getKey(), AUTH_MANAGEMENT_API_AUTH_MAPPING.getValue());
        AuthenticationResolver authenticationResolver = new DefaultAuthenticationResolver(authMappings);

        neoClientFactoryWithResolver = new NeoClientFactory(authenticationResolver);
    }

    @Test
    public void createConfigurationService_singleAuthenticationTest() {
        DestinationConfigurationService configurationService = neoClientFactory
                .getDestinationConfigurationService(REGION_ALIAS);

        assertNotNull(configurationService);
    }

    @Test
    public void createAuthorizationManagementService_singleAuthenticationTest() {
        AuthorizationManagementService authorizationManagementService = neoClientFactory
                .getAuthorizationManagementService(REGION_ALIAS, SUBACCOUNT);

        assertNotNull(authorizationManagementService);
    }

    @Test
    public void createConfigurationService_multipleAuthenticationTest() {
        DestinationConfigurationService configurationService = neoClientFactoryWithResolver
                .getDestinationConfigurationService(REGION_ALIAS);

        assertNotNull(configurationService);
    }

    @Test
    public void createAuthorizationManagementService_multipleAuthenticationTest() {
        AuthorizationManagementService authorizationManagementService = neoClientFactoryWithResolver
                .getAuthorizationManagementService(REGION_ALIAS, SUBACCOUNT);

        assertNotNull(authorizationManagementService);
    }
}