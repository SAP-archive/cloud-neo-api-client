package com.sap.cloud.rest.neo.api.client.service.configuration;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.rest.api.client.config.RestApiClientConfig;
import com.sap.cloud.rest.neo.api.client.RestApiClientAbstractTest;
import com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.AuthenticationType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.BasicDestination;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.Destination;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.DestinationType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.ProxyType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.SAMLBearerDestination;

public class DestinationConfigurationRestApiClientTest extends RestApiClientAbstractTest {

    private DestinationConfigurationService desinationConfigurationService;

    private HashMap<String, String> additionalProperties = new HashMap<>();
    private SAMLBearerDestination samlBearerDestination;
    private BasicDestination basicDestination;
    private Destination destination;

    @Before
    public void before() {
        additionalProperties.put("userIdSource", "user-id-source");
        additionalProperties.put("nameIdFormat", "name-id-format");
        additionalProperties.put("authnContextClassRef", "authn-context-class-ref");

        samlBearerDestination = new SAMLBearerDestination(
                "test_oauth_dest", "http//example.com", ProxyType.INTERNET, "test_audience",
                "client-key".toCharArray(), "token-service-url", "token-service-user",
                "token-service-password".toCharArray(), additionalProperties);

        basicDestination = new BasicDestination("test_basic_dest", "http//example.com", ProxyType.INTERNET,
                DestinationType.HTTP, "username", "password".toCharArray(), additionalProperties);

        destination = new Destination("test_dest", "http//example.com", AuthenticationType.NO_AUTH,
                ProxyType.INTERNET, DestinationType.HTTP, additionalProperties);
        ;
    }

    @Override
    public void initService() {
        RestApiClientConfig config = new RestApiClientConfig(VALID_HOST);
        desinationConfigurationService = new DestinationConfigurationRestApiClient(config, httpClientProvider);
    }

    @Override
    public void executeSampleRequest() throws Exception {
        desinationConfigurationService.deleteDestinationOnApplicationLevel("test-account", "test-app", "test-config");
    }

    @Test
    public void upsertSAMLBearerDestinationOnTenantLevel_postRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.upsertDestinationOnTenantLevel("test_subaccount",
                        samlBearerDestination),
                "POST",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/connectivity/");
    }

    @Test
    public void retrieveSAMLBearerDestinationOnTenantLevel_getRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(
                () -> desinationConfigurationService.retrieveSAMLBearerDestinationOnTenantLevel(
                        "test_subaccount", "test_oauth_dest"),
                "GET",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/connectivity/test_oauth_dest",
                convertObjectToProperties(samlBearerDestination));
    }

    @Test
    public void deleteSAMLBearerDestinationOnTenantLevel_deleteRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.deleteDestinationOnTenantLevel("test_subaccount",
                        "test_oauth_dest"),
                "DELETE",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/connectivity/test_oauth_dest");
    }

    @Test
    public void upsertBasicDestinationOnTenantLevel_postRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.upsertDestinationOnTenantLevel("test_subaccount",
                        basicDestination),
                "POST",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/connectivity/");
    }

    @Test
    public void retrieveBasicDestinationOnTenantLevel_getRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(
                () -> desinationConfigurationService.retrieveBasicDestinationOnTenantLevel("test_subaccount",
                        "test_basic_dest"),
                "GET",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/connectivity/test_basic_dest",
                convertObjectToProperties(basicDestination));
    }

    @Test
    public void deleteBasicDestinationOnTenantLevel_deleteRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.deleteDestinationOnTenantLevel("test_subaccount",
                        "test_basic_dest"),
                "DELETE",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/connectivity/test_basic_dest");
    }

    @Test
    public void upsertDestinationOnTenantLevel_postRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.upsertDestinationOnTenantLevel("test_subaccount",
                        destination),
                "POST",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/connectivity/");
    }

    @Test
    public void retrieveDestinationOnTenantLevel_getRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(
                () -> desinationConfigurationService.retrieveDestinationOnTenantLevel("test_subaccount", "test_dest"),
                "GET",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/connectivity/test_dest",
                convertObjectToProperties(destination));
    }

    @Test
    public void deleteDestinationOnTenantLevel_deleteRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.deleteDestinationOnTenantLevel("test_subaccount", "test_dest"),
                "DELETE",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/connectivity/test_dest");
    }

    @Test
    public void upsertSAMLBearerDestinationOnApplicationLevel_postRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.upsertDestinationOnApplicationLevel("test_subaccount", "test_app",
                        samlBearerDestination),
                "POST",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/appliances/test_app/components/web/base/connectivity/");
    }

    @Test
    public void retrieveSAMLBearernDestinationOnApplicationLevel_getRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(
                () -> desinationConfigurationService.retrieveSAMLBearerDestinationOnApplicationLevel(
                        "test_subaccount", "test_app", "test_oauth_dest"),
                "GET",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/appliances/test_app/components/web/base/connectivity/test_oauth_dest",
                convertObjectToProperties(samlBearerDestination));
    }

    @Test
    public void deleteSAMLBearerDestinationOnApplicationLevel_deleteRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.deleteDestinationOnApplicationLevel("test_subaccount", "test_app",
                        "test_oauth_dest"),
                "DELETE",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/appliances/test_app/components/web/base/connectivity/test_oauth_dest");
    }

    @Test
    public void upsertBasicOnApplicationLevel_postRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.upsertDestinationOnApplicationLevel("test_subaccount", "test_app",
                        basicDestination),
                "POST",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/appliances/test_app/components/web/base/connectivity/");
    }

    @Test
    public void retrieveBasicDestinationOnApplicationLevel_getRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(
                () -> desinationConfigurationService.retrieveBasicDestinationOnApplicationLevel(
                        "test_subaccount", "test_app", "test_basic_dest"),
                "GET",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/appliances/test_app/components/web/base/connectivity/test_basic_dest",
                convertObjectToProperties(basicDestination));
    }

    @Test
    public void deleteBasicDestinationOnApplicationLevel_deleteRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.deleteDestinationOnApplicationLevel("test_subaccount", "test_app",
                        "test_basic_dest"),
                "DELETE",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/appliances/test_app/components/web/base/connectivity/test_basic_dest");
    }

    @Test
    public void upsertDestinationOnApplicationLevel_postRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.upsertDestinationOnApplicationLevel("test_subaccount", "test_app",
                        destination),
                "POST",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/appliances/test_app/components/web/base/connectivity/");
    }

    @Test
    public void retrieveDestinationOnApplicationLevel_getRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(
                () -> desinationConfigurationService.retrieveDestinationOnApplicationLevel("test_subaccount",
                        "test_app", "test_dest"),
                "GET",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/appliances/test_app/components/web/base/connectivity/test_dest",
                convertObjectToProperties(destination));
    }

    @Test
    public void deleteDestinationOnApplicationLevel_deleteRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.deleteDestinationOnApplicationLevel("test_subaccount", "test_app",
                        "test_dest"),
                "DELETE",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_subaccount/appliances/test_app/components/web/base/connectivity/test_dest");
    }

    @Test
    public void upsertSAMLBearerDestinationOnSubscriptionLevel_postRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.upsertDestinationOnSubscriptionLevel("test_consumer_subaccount",
                        "test_provider_subaccount", "test_app", samlBearerDestination),
                "POST",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_consumer_subaccount/appliances/test_app/components/web/base/connectivity/provideraccount/test_provider_subaccount/providerapplication/test_app/");
    }

    @Test
    public void retrieveSAMLBearefDestinationOnSubscriptionLevel_getRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(
                () -> desinationConfigurationService.retrieveSAMLBearerDestinationOnSubscriptionLevel(
                        "test_consumer_subaccount", "test_provider_subaccount", "test_app", "test_oauth_dest"),
                "GET",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_consumer_subaccount/appliances/test_app/components/web/base/connectivity/provideraccount/test_provider_subaccount/providerapplication/test_app/test_oauth_dest",
                convertObjectToProperties(samlBearerDestination));
    }

    @Test
    public void deleteSAMLBearerDestinationOnSubscriptionLevel_deleteRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.deleteDestinationOnSubscriptionLevel("test_consumer_subaccount",
                        "test_provider_subaccount", "test_app", "test_oauth_dest"),
                "DELETE",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_consumer_subaccount/appliances/test_app/components/web/base/connectivity/provideraccount/test_provider_subaccount/providerapplication/test_app/test_oauth_dest");
    }

    @Test
    public void upsertBasicDestinationOnSubscriptionLevel_postRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.upsertDestinationOnSubscriptionLevel("test_consumer_subaccount",
                        "test_provider_subaccount", "test_app", basicDestination),
                "POST",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_consumer_subaccount/appliances/test_app/components/web/base/connectivity/provideraccount/test_provider_subaccount/providerapplication/test_app/");
    }

    @Test
    public void retrieveBasicDestinationOnSubscriptionLevel_getRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(
                () -> desinationConfigurationService.retrieveBasicDestinationOnSubscriptionLevel(
                        "test_consumer_subaccount", "test_provider_subaccount", "test_app", "test_bacis_dest"),
                "GET",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_consumer_subaccount/appliances/test_app/components/web/base/connectivity/provideraccount/test_provider_subaccount/providerapplication/test_app/test_bacis_dest",
                convertObjectToProperties(basicDestination));
    }

    @Test
    public void deleteBasicDestinationOnSubscriptionLevel_deleteRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.deleteDestinationOnSubscriptionLevel("test_consumer_subaccount",
                        "test_provider_subaccount", "test_app", "test_bacis_dest"),
                "DELETE",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_consumer_subaccount/appliances/test_app/components/web/base/connectivity/provideraccount/test_provider_subaccount/providerapplication/test_app/test_bacis_dest");
    }

    @Test
    public void upsertDestinationOnSubscriptionLevel_postRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.upsertDestinationOnSubscriptionLevel("test_consumer_subaccount",
                        "test_provider_subaccount", "test_app", destination),
                "POST",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_consumer_subaccount/appliances/test_app/components/web/base/connectivity/provideraccount/test_provider_subaccount/providerapplication/test_app/");
    }

    @Test
    public void retrieveDestinationOnSubscriptionLevel_getRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(
                () -> desinationConfigurationService.retrieveDestinationOnSubscriptionLevel("test_consumer_subaccount",
                        "test_provider_subaccount", "test_app", "test_dest"),
                "GET",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_consumer_subaccount/appliances/test_app/components/web/base/connectivity/provideraccount/test_provider_subaccount/providerapplication/test_app/test_dest",
                convertObjectToProperties(destination));
    }

    @Test
    public void deleteDestinationOnSubscriptionLevel_deleteRequestToTheRightUrl()
            throws Exception {
        verifyRequestMethodAndUrl(
                () -> desinationConfigurationService.deleteDestinationOnSubscriptionLevel("test_consumer_subaccount",
                        "test_provider_subaccount", "test_app", "test_dest"),
                "DELETE",
                "https://example.com/configuration/api/rest/oauth/SPACES/test_consumer_subaccount/appliances/test_app/components/web/base/connectivity/provideraccount/test_provider_subaccount/providerapplication/test_app/test_dest");
    }

    private String convertObjectToProperties(Destination destination) throws Exception {
        DestinationDto destinationDto = destination.toDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObj = objectMapper.writeValueAsString(destinationDto.getProperties());

        StringWriter sw = new StringWriter();
        Properties props = objectMapper.readValue(jsonObj, Properties.class);
        props.store(sw, null);
        return sw.toString();
    }
}
