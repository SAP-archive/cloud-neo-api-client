package com.sap.cloud.rest.neo.api.client.integration;

import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getPlatformClientId;
import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getPlatformClientSecret;
import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getProviderApplication;
import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getProviderSubaccount;
import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getRegionAlias;
import static com.sap.cloud.rest.neo.api.client.integration.NeoApiClientIntegrationTest.getSubaccount;
import static com.sap.cloud.rest.neo.api.client.tests.utils.JsonUtil.assertJsonEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sap.cloud.rest.api.client.auth.oauth.OAuthAuthentication;
import com.sap.cloud.rest.neo.api.client.config.NeoClientFactory;
import com.sap.cloud.rest.neo.api.client.service.configuration.DestinationConfigurationService;
import com.sap.cloud.rest.neo.api.client.service.configuration.exceptions.DestinationNotFoundException;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.AuthenticationType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.BasicDestination;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.Destination;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.DestinationType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.ProxyType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.SAMLBearerDestination;
import com.sap.cloud.rest.neo.api.client.service.oauth.platform.PlatformClientsOAuthAuthenticationBuilder;

public class DestinationConfigurationServiceIntegrationTest {

    private static final String NAME = "test-destination";
    private static final String NAME_BASIC = "test-destination-basic";
    private static final String NAME_SAML = "test-destination-saml";
    private static final String URL_ONE = "https://example1.com";
    private static final String URL_TWO = "https://example1.com";
    private static final ProxyType PROXY_TYPE = ProxyType.INTERNET;
    private static final DestinationType DESTINATION_TYPE = DestinationType.HTTP;
    private static final String AUDIENCE = "audience";
    private static final char[] CLIENT_KEY = "clientKey".toCharArray();
    private static final String TOKEN_SERVICE_URL = "https://token.service.com";
    private static final String TOKEN_SERVICE_USER = "tokenServiceUser";
    private static final char[] TOKEN_SERVICE_PASSWORD = "tokenServicePassword".toCharArray();
    private static final String USER = "user";
    private static final char[] PASSWORD = "password".toCharArray();

    private static final String USER_ID_SOURCE = "userIdSource";
    private static final String NAME_ID_FORMAT = "nameIdFormat";
    private static final String AUTHN_CONTEXT_CLASS_REF = "authnContextClassRef";
    private static final HashMap<String, String> ADDITIONAL_ATTRIBUTES;

    private static final Destination CONNECTIVITY_CONFIGURATION_ONE;
    private static final Destination CONNECTIVITY_CONFIGURATION_TWO;

    private static final BasicDestination BASIC_CONNECTIVITY_CONFIGURATION_ONE;
    private static final BasicDestination BASIC_CONNECTIVITY_CONFIGURATION_TWO;

    private static final SAMLBearerDestination OAUTH_CONNECTIVITY_CONFIGURATION_ONE;
    private static final SAMLBearerDestination OAUTH_CONNECTIVITY_CONFIGURATION_TWO;

    static {
        ADDITIONAL_ATTRIBUTES = new HashMap<>(3);
        ADDITIONAL_ATTRIBUTES.put(USER_ID_SOURCE, USER_ID_SOURCE);
        ADDITIONAL_ATTRIBUTES.put(NAME_ID_FORMAT, NAME_ID_FORMAT);
        ADDITIONAL_ATTRIBUTES.put(AUTHN_CONTEXT_CLASS_REF, AUTHN_CONTEXT_CLASS_REF);

        CONNECTIVITY_CONFIGURATION_ONE = new Destination(
                NAME, URL_ONE, AuthenticationType.NO_AUTH, PROXY_TYPE, DestinationType.HTTP, ADDITIONAL_ATTRIBUTES);

        CONNECTIVITY_CONFIGURATION_TWO = new Destination(
                NAME, URL_TWO, AuthenticationType.NO_AUTH, PROXY_TYPE, DestinationType.HTTP, ADDITIONAL_ATTRIBUTES);

        BASIC_CONNECTIVITY_CONFIGURATION_ONE = new BasicDestination(
                NAME_BASIC, URL_ONE, PROXY_TYPE, DESTINATION_TYPE, USER, PASSWORD, ADDITIONAL_ATTRIBUTES);

        BASIC_CONNECTIVITY_CONFIGURATION_TWO = new BasicDestination(
                NAME_BASIC, URL_TWO, PROXY_TYPE, DESTINATION_TYPE, USER, PASSWORD, ADDITIONAL_ATTRIBUTES);

        OAUTH_CONNECTIVITY_CONFIGURATION_ONE = new SAMLBearerDestination(
                NAME_SAML, URL_ONE, PROXY_TYPE, AUDIENCE, CLIENT_KEY, TOKEN_SERVICE_URL,
                TOKEN_SERVICE_USER, TOKEN_SERVICE_PASSWORD, ADDITIONAL_ATTRIBUTES);

        OAUTH_CONNECTIVITY_CONFIGURATION_TWO = new SAMLBearerDestination(
                NAME_SAML, URL_TWO, PROXY_TYPE, AUDIENCE, CLIENT_KEY, TOKEN_SERVICE_URL,
                TOKEN_SERVICE_USER, TOKEN_SERVICE_PASSWORD, ADDITIONAL_ATTRIBUTES);

    }

    private static DestinationConfigurationService destinationConfigurationService;

    private static String applicationName;

    private static String providerSubaccount;
    private static String providerApplication;

    private static String subaccount;

    @BeforeClass
    public static void setup() {
        subaccount = getSubaccount();
        applicationName = System.getProperty("applicationName");
        providerSubaccount = getProviderSubaccount();
        providerApplication = getProviderApplication();

        OAuthAuthentication authentication = PlatformClientsOAuthAuthenticationBuilder.getBuilder()
                .subaccount(getSubaccount())
                .regionAlias(getRegionAlias())
                .clientID(getPlatformClientId())
                .clientSecret(getPlatformClientSecret().toCharArray())
                .build();
        // Create Neo API Client Factory
        NeoClientFactory neoClientFactory = new NeoClientFactory(authentication);
        destinationConfigurationService = neoClientFactory.getDestinationConfigurationService(getRegionAlias());

        cleanUpDestinations();
    }

    private static void cleanUpDestinations() {
        cleanUpApplicationLevelDestinationIfExists();
        cleanUpSubscriptionLevelDestinationIfExists();
    }

    @AfterClass
    public static void cleanUpPlatformClients() {
    }

    @Test
    public void destinationConfigurationTenantLevelIntegrationTest() throws JSONException {
        upsertDestinationOnTenantLevel(CONNECTIVITY_CONFIGURATION_ONE);

        Destination destinationBeforeUpdate = retrieveDestinationOnTenantLevel();
        assertJsonEquals(CONNECTIVITY_CONFIGURATION_ONE.toString(), destinationBeforeUpdate.toString());

        upsertDestinationOnTenantLevel(CONNECTIVITY_CONFIGURATION_TWO);

        Destination destinationAfterUpdate = retrieveDestinationOnTenantLevel();
        assertJsonEquals(CONNECTIVITY_CONFIGURATION_TWO.toString(), destinationAfterUpdate.toString());

        deleteDestinationsOnTenantLevel(NAME);

        try {
            retrieveDestinationOnTenantLevel();
            fail("Destination on tenant level wasn't deleted successfully.");
        } catch (DestinationNotFoundException e) {
        }
    }

    @Test
    public void basicDestinationConfigurationTenantLevelIntegrationTest() throws JSONException {
        upsertDestinationOnTenantLevel(BASIC_CONNECTIVITY_CONFIGURATION_ONE);

        BasicDestination basicDestinationBeforeUpdate = retrieveBasicOnTenantLevel();
        assertJsonEquals(BASIC_CONNECTIVITY_CONFIGURATION_ONE.toString(), basicDestinationBeforeUpdate.toString());

        upsertDestinationOnTenantLevel(BASIC_CONNECTIVITY_CONFIGURATION_TWO);

        BasicDestination basicDestinationAfterUpdate = retrieveBasicOnTenantLevel();
        assertJsonEquals(BASIC_CONNECTIVITY_CONFIGURATION_TWO.toString(), basicDestinationAfterUpdate.toString());

        deleteDestinationsOnTenantLevel(NAME_BASIC);

        try {
            retrieveBasicOnTenantLevel();
            fail("Basic Destination on tenant level wasn't deleted successfully.");
        } catch (DestinationNotFoundException e) {
        }
    }

    @Test
    public void samlBearerDestinationConfigurationTenantLevelIntegrationTest() throws JSONException {
        upsertDestinationOnTenantLevel(OAUTH_CONNECTIVITY_CONFIGURATION_ONE);

        SAMLBearerDestination samlDestinationBeforeUpdate = retrieveOAuthSAMLDestinationOnTenantLevel();
        assertJsonEquals(OAUTH_CONNECTIVITY_CONFIGURATION_ONE.toString(), samlDestinationBeforeUpdate.toString());

        upsertDestinationOnTenantLevel(OAUTH_CONNECTIVITY_CONFIGURATION_TWO);

        SAMLBearerDestination samlDestinationAfterUpdate = retrieveOAuthSAMLDestinationOnTenantLevel();
        assertJsonEquals(OAUTH_CONNECTIVITY_CONFIGURATION_TWO.toString(), samlDestinationAfterUpdate.toString());

        deleteDestinationsOnTenantLevel(NAME_SAML);

        try {
            retrieveOAuthSAMLDestinationOnTenantLevel();
            fail("Basic Destination on tenant level wasn't deleted successfully.");
        } catch (DestinationNotFoundException e) {
        }
    }

    @Test
    public void destinationConfigurationApplicationLevelIntegrationTest() throws JSONException {
        upsertDestinationOnApplicationLevel(CONNECTIVITY_CONFIGURATION_ONE);

        Destination destinationBeforeUpdate = retrieveDestinationOnApplicationLevel();
        assertJsonEquals(CONNECTIVITY_CONFIGURATION_ONE.toString(), destinationBeforeUpdate.toString());

        upsertDestinationOnApplicationLevel(CONNECTIVITY_CONFIGURATION_TWO);

        Destination destinationAfterUpdate = retrieveDestinationOnApplicationLevel();
        assertJsonEquals(CONNECTIVITY_CONFIGURATION_ONE.toString(), destinationAfterUpdate.toString());

        deleteDestinationsOnApplicationLevel(NAME);

        try {
            retrieveDestinationOnApplicationLevel();
            fail("Destination on application level wasn't deleted successfully.");
        } catch (DestinationNotFoundException e) {
        }
    }

    @Test
    public void basicDestinationConfigurationApplicationLevelIntegrationTest() throws JSONException {
        upsertDestinationOnApplicationLevel(BASIC_CONNECTIVITY_CONFIGURATION_ONE);

        BasicDestination basicDestinationBeforeUpdate = retrieveBasicDestinationOnApplicationLevel();
        assertJsonEquals(BASIC_CONNECTIVITY_CONFIGURATION_ONE.toString(), basicDestinationBeforeUpdate.toString());

        upsertDestinationOnApplicationLevel(BASIC_CONNECTIVITY_CONFIGURATION_TWO);

        BasicDestination basicDestinationAfterUpdate = retrieveBasicDestinationOnApplicationLevel();
        assertJsonEquals(BASIC_CONNECTIVITY_CONFIGURATION_ONE.toString(), basicDestinationAfterUpdate.toString());

        deleteDestinationsOnApplicationLevel(NAME_BASIC);

        try {
            retrieveBasicDestinationOnApplicationLevel();
            fail("Basic Destination on application level wasn't deleted successfully.");
        } catch (DestinationNotFoundException e) {
        }
    }

    @Test
    public void samlBearerDestinationConfigurationApplicationLevelIntegrationTest() throws JSONException {
        upsertDestinationOnApplicationLevel(OAUTH_CONNECTIVITY_CONFIGURATION_ONE);

        SAMLBearerDestination samlDestinationBeforeUpdate = retrieveOAuthSAMLDestinationOnApplicationLevel();
        assertJsonEquals(OAUTH_CONNECTIVITY_CONFIGURATION_ONE.toString(), samlDestinationBeforeUpdate.toString());

        upsertDestinationOnApplicationLevel(OAUTH_CONNECTIVITY_CONFIGURATION_TWO);

        SAMLBearerDestination samlDestinationAfterUpdate = retrieveOAuthSAMLDestinationOnApplicationLevel();
        assertJsonEquals(OAUTH_CONNECTIVITY_CONFIGURATION_ONE.toString(), samlDestinationAfterUpdate.toString());

        deleteDestinationsOnApplicationLevel(NAME_SAML);

        try {
            retrieveOAuthSAMLDestinationOnApplicationLevel();
            fail("SAML Bearer Destination on application level wasn't deleted successfully.");
        } catch (DestinationNotFoundException e) {
        }
    }

    @Test
    public void destinationConfigurationSubsriptionLevelIntegrationTest() throws JSONException {
        upsertDestinationOnSubscriptionLevel(CONNECTIVITY_CONFIGURATION_ONE);

        Destination destinationBeforeUpdate = retrieveDestinationOnSubscriptionLevel();
        assertJsonEquals(CONNECTIVITY_CONFIGURATION_ONE.toString(), destinationBeforeUpdate.toString());

        upsertDestinationOnSubscriptionLevel(CONNECTIVITY_CONFIGURATION_TWO);

        Destination destinationAfterUpdate = retrieveDestinationOnSubscriptionLevel();
        assertJsonEquals(CONNECTIVITY_CONFIGURATION_ONE.toString(), destinationAfterUpdate.toString());

        deleteDestinationsOnSubscriptionLevel(NAME);

        try {
            retrieveDestinationOnSubscriptionLevel();
            fail("Destination on Subscription level wasn't deleted successfully.");
        } catch (DestinationNotFoundException e) {
        }
    }

    @Test
    public void basicDestinationConfigurationSubsriptionLevelIntegrationTest() throws JSONException {
        upsertDestinationOnSubscriptionLevel(BASIC_CONNECTIVITY_CONFIGURATION_ONE);

        BasicDestination basicDestinationBeforeUpdate = retrieveBasicDestinationOnSubscriptionLevel();
        assertJsonEquals(BASIC_CONNECTIVITY_CONFIGURATION_ONE.toString(), basicDestinationBeforeUpdate.toString());

        upsertDestinationOnSubscriptionLevel(BASIC_CONNECTIVITY_CONFIGURATION_TWO);

        BasicDestination basicDestinationAfterUpdate = retrieveBasicDestinationOnSubscriptionLevel();
        assertJsonEquals(BASIC_CONNECTIVITY_CONFIGURATION_ONE.toString(), basicDestinationAfterUpdate.toString());

        deleteDestinationsOnSubscriptionLevel(NAME_BASIC);

        try {
            retrieveBasicDestinationOnSubscriptionLevel();
            fail("Destination on Subscription level wasn't deleted successfully.");
        } catch (DestinationNotFoundException e) {
        }
    }

    @Test
    public void samlBearerDestinationConfigurationSubsriptionLevelIntegrationTest() throws JSONException {
        upsertDestinationOnSubscriptionLevel(OAUTH_CONNECTIVITY_CONFIGURATION_ONE);

        SAMLBearerDestination samlDestinationBeforeUpdate = retrieveOAuthSAMLDestinationOnSubscriptionLevel();
        assertJsonEquals(OAUTH_CONNECTIVITY_CONFIGURATION_ONE.toString(), samlDestinationBeforeUpdate.toString());

        upsertDestinationOnSubscriptionLevel(OAUTH_CONNECTIVITY_CONFIGURATION_TWO);

        SAMLBearerDestination samlDestinationAfterUpdate = retrieveOAuthSAMLDestinationOnSubscriptionLevel();
        assertJsonEquals(OAUTH_CONNECTIVITY_CONFIGURATION_ONE.toString(), samlDestinationAfterUpdate.toString());

        deleteDestinationsOnSubscriptionLevel(NAME_SAML);

        try {
            retrieveOAuthSAMLDestinationOnSubscriptionLevel();
            fail("SAML Bearer Destination on Subscription level wasn't deleted successfully.");
        } catch (DestinationNotFoundException e) {
        }
    }

    private Destination retrieveDestinationOnTenantLevel() {
        return destinationConfigurationService.retrieveDestinationOnTenantLevel(subaccount, NAME);
    }

    private BasicDestination retrieveBasicOnTenantLevel() {
        return destinationConfigurationService.retrieveBasicDestinationOnTenantLevel(
                subaccount, NAME_BASIC);
    }

    private SAMLBearerDestination retrieveOAuthSAMLDestinationOnTenantLevel() {
        return destinationConfigurationService.retrieveSAMLBearerDestinationOnTenantLevel(
                subaccount, NAME_SAML);
    }

    private Destination retrieveDestinationOnApplicationLevel() {
        return destinationConfigurationService.retrieveDestinationOnApplicationLevel(
                subaccount, applicationName, NAME);
    }

    private BasicDestination retrieveBasicDestinationOnApplicationLevel() {
        return destinationConfigurationService.retrieveBasicDestinationOnApplicationLevel(
                subaccount, applicationName, NAME_BASIC);
    }

    private SAMLBearerDestination retrieveOAuthSAMLDestinationOnApplicationLevel() {
        return destinationConfigurationService.retrieveSAMLBearerDestinationOnApplicationLevel(
                subaccount, applicationName, NAME_SAML);
    }

    private Destination retrieveDestinationOnSubscriptionLevel() {
        return destinationConfigurationService.retrieveDestinationOnSubscriptionLevel(
                subaccount, providerSubaccount, providerApplication, NAME);
    }

    private BasicDestination retrieveBasicDestinationOnSubscriptionLevel() {
        return destinationConfigurationService.retrieveBasicDestinationOnSubscriptionLevel(
                subaccount, providerSubaccount, providerApplication, NAME_BASIC);
    }

    private SAMLBearerDestination retrieveOAuthSAMLDestinationOnSubscriptionLevel() {
        return destinationConfigurationService.retrieveSAMLBearerDestinationOnSubscriptionLevel(
                subaccount, providerSubaccount, providerApplication, NAME_SAML);
    }

    private void upsertDestinationOnTenantLevel(Destination destination) {
        destinationConfigurationService.upsertDestinationOnTenantLevel(
                subaccount, destination);
    }

    private void upsertDestinationOnApplicationLevel(Destination destination) {
        destinationConfigurationService.upsertDestinationOnApplicationLevel(
                subaccount, applicationName, destination);
    }

    private void upsertDestinationOnSubscriptionLevel(Destination destination) {
        destinationConfigurationService.upsertDestinationOnSubscriptionLevel(
                subaccount, providerSubaccount, providerApplication, destination);
    }

    private void deleteDestinationsOnTenantLevel(String name) {
        destinationConfigurationService.deleteDestinationOnTenantLevel(subaccount, name);
    }

    private void deleteDestinationsOnApplicationLevel(String name) {
        destinationConfigurationService.deleteDestinationOnApplicationLevel(subaccount, applicationName, name);
    }

    private void deleteDestinationsOnSubscriptionLevel(String name) {
        destinationConfigurationService.deleteDestinationOnSubscriptionLevel(
                subaccount, providerSubaccount, providerApplication, name);
    }

    private static void cleanUpApplicationLevelDestinationIfExists() {
        try {
            destinationConfigurationService.retrieveSAMLBearerDestinationOnApplicationLevel(
                    subaccount, applicationName, NAME);
        } catch (DestinationNotFoundException e) {
            return;
        }
        destinationConfigurationService.deleteDestinationOnApplicationLevel(subaccount, applicationName, NAME);
    }

    private static void cleanUpSubscriptionLevelDestinationIfExists() {
        try {
            destinationConfigurationService.retrieveSAMLBearerDestinationOnSubscriptionLevel(
                    subaccount, providerSubaccount, providerApplication, NAME);
        } catch (DestinationNotFoundException e) {
            return;
        }
        destinationConfigurationService.deleteDestinationOnSubscriptionLevel(
                subaccount, providerSubaccount, providerApplication, NAME);
    }
}
