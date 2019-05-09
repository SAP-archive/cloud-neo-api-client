package com.sap.cloud.rest.neo.api.client.service.configuration;

import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.rest.api.client.RequestBuilder;
import com.sap.cloud.rest.api.client.RestApiClient;
import com.sap.cloud.rest.api.client.config.RestApiClientConfig;
import com.sap.cloud.rest.api.client.handler.StatusCodeHandler;
import com.sap.cloud.rest.api.client.http.HttpClientProvider;
import com.sap.cloud.rest.api.client.model.Request;
import com.sap.cloud.rest.api.client.model.multipart.MultipartEntity;
import com.sap.cloud.rest.api.client.utils.PropertiesResponseHandler;
import com.sap.cloud.rest.neo.api.client.service.configuration.dto.BasicDestinationDto;
import com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto;
import com.sap.cloud.rest.neo.api.client.service.configuration.dto.SAMLBearerDestinationDto;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.BasicDestination;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.Destination;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.SAMLBearerDestination;

/**
 * Implements the {@link DestinationConfigurationService} by making HTTP
 * requests to Configuration REST API on SAP CP. This is the default used
 * implementation.
 *
 */
public class DestinationConfigurationRestApiClient extends RestApiClient implements DestinationConfigurationService {

    private static final String BASE_API_PATH = "/configuration/api/rest";

    static final String READ_DESTINATION_SCOPE = "readDestination";
    static final String MANAGE_DESTINATION_SCOPE = "manageDestination";

    private static final String OAUTH_API_PATH_TENANT_LEVEL = "/oauth/SPACES/{0}/connectivity/";
    private static final String DEFINED_CONFIGURATION_OAUTH_API_PATH_TENANT_LEVEL = "/oauth/SPACES/{0}/connectivity/{1}";

    private static final String OAUTH_API_PATH_APPLICATION_LEVEL = "/oauth/SPACES/{0}/appliances/{1}/components/web/base/connectivity/";
    private static final String DEFINED_CONFIGURATION_OAUTH_API_PATH_APPLICATION_LEVEL = "/oauth/SPACES/{0}/appliances/{1}/components/web/base/connectivity/{2}";

    private static final String OAUTH_API_PATH_SUBSCRIPTION_LEVEL = "/oauth/SPACES/{0}/appliances/{1}/components/web/base/connectivity/provideraccount/{2}/providerapplication/{3}/";
    private static final String DEFINED_CONFIGURATION_OAUTH_API_PATH_SUBSCRIPTION_LEVEL = "/oauth/SPACES/{0}/appliances/{1}/components/web/base/connectivity/provideraccount/{2}/providerapplication/{3}/{4}";

    private static final String UPSERTING_CONFIGURATION_TENANT_LEVEL_WAS_SUCCESSFUL_MSG = "Upserting destination with name [{}] in subaccount [{}] was successful.";
    private static final String RETRIEVING_CONFIGURATION_TENANT_LEVEL_WAS_SUCCESSFUL_MSG = "Retrieving destination with name [{}] in subaccount [{}] was successful.";
    private static final String DELETING_CONFIGURATION_TENANT_LEVEL_WAS_SUCCESSFUL_MSG = "Deleting destination with name [{}] in subaccount [{}] was successful.";

    private static final String UPSERTING_CONFIGURATION_APPLICATION_LEVEL_WAS_SUCCESSFUL_MSG = "Upserting destination with name [{}] for application [{}] in subaccount [{}] was successful.";
    private static final String RETRIEVING_CONFIGURATION_APPLICATION_LEVEL_WAS_SUCCESSFUL_MSG = "Retrieving destination with name [{}] defined for application [{}] in subaccount [{}] was successful.";
    private static final String DELETING_CONFIGURATION_APPLICATION_LEVEL_WAS_SUCCESSFUL_MSG = "Deleting destination with name [{}] defined for application [{}] in subaccount [{}] was successful.";

    private static final String UPSERTING_CONFIGURATION_SUBSCRIPTION_LEVEL_WAS_SUCCESSFUL_MSG = "Upserting destination with name [{}] from application [{}] which is in provider subaccount [{}] for subscribed consumer subaccount [{}] was successful.";
    private static final String RETRIEVING_CONFIGURATION_SUBSCRIPTION_LEVEL_WAS_SUCCESSFUL_MSG = "Retrieving destination with name [{}] from application [{}] which is in provider subaccount [{}] for subscribed consumer subaccount [{}] was successful.";
    private static final String DELETING_CONFIGURATION_SUBSCRIPTION_LEVEL_WAS_SUCCESSFUL_MSG = "Deleting destination with name [{}] from application [{}] which is in provider subaccount [{}] for subscribed consumer subaccount [{}] was successful.";

    private static final Logger log = LoggerFactory.getLogger(DestinationConfigurationRestApiClient.class);

    /**
     * Creates a {@link DestinationConfigurationService} instance.
     * 
     * @param config
     *            the configuration used to construct the {@link RestApiClient}
     */
    public DestinationConfigurationRestApiClient(RestApiClientConfig config) {
        super(config);
    }

    /**
     * A constructor which allows providing a custom {@link HttpClientProvider}.
     * 
     * @param config
     *            the configuration used to construct the {@link RestApiClient}.
     * @param httpClientProvider
     *            the HTTP client provider to be used to create HTTP clients.
     */
    public DestinationConfigurationRestApiClient(RestApiClientConfig config, HttpClientProvider httpClientProvider) {
        super(config, httpClientProvider);
    }

    @Override
    protected String getApiPath() {
        return BASE_API_PATH;
    }

    @Override
    public void upsertDestinationOnTenantLevel(String subaccountId, Destination destination) {
        DestinationDto destinationDto = destination.toDto();
        String destinationName = destination.getName();

        Request<MultipartEntity<DestinationDto>> upsertConfigurationOnTenantLevelRequest = RequestBuilder
                .postRequest(DestinationDto.class)
                .multipartEntity(destinationName, destinationDto)
                .uri(buildRequestUri(OAUTH_API_PATH_TENANT_LEVEL, subaccountId))
                .buildMultipart();

        StatusCodeHandler customStatusCodeHandler = DestinationConfigurationServiceStatusCodeHandler
                .create(subaccountId, destinationName, MANAGE_DESTINATION_SCOPE);

        execute(upsertConfigurationOnTenantLevelRequest, customStatusCodeHandler);

        log.info(UPSERTING_CONFIGURATION_TENANT_LEVEL_WAS_SUCCESSFUL_MSG, destination.getName(), subaccountId);
    }

    @Override
    public Destination retrieveDestinationOnTenantLevel(String subaccountId, String destinationName) {
        PropertiesResponseHandler<DestinationDto> customResponseHandler = new PropertiesResponseHandler<>(
                DestinationDto.class);
        return (Destination) retrieveDestinationOnTenantLevel(subaccountId,
                destinationName, customResponseHandler);
    }

    @Override
    public SAMLBearerDestination retrieveSAMLBearerDestinationOnTenantLevel(
            String subaccountId, String destinationName) {
        PropertiesResponseHandler<SAMLBearerDestinationDto> customResponseHandler = new PropertiesResponseHandler<>(
                SAMLBearerDestinationDto.class);
        return (SAMLBearerDestination) retrieveDestinationOnTenantLevel(subaccountId,
                destinationName, customResponseHandler);
    }

    @Override
    public BasicDestination retrieveBasicDestinationOnTenantLevel(String subaccountId, String destinationName) {
        PropertiesResponseHandler<BasicDestinationDto> customResponseHandler = new PropertiesResponseHandler<>(
                BasicDestinationDto.class);
        return (BasicDestination) retrieveDestinationOnTenantLevel(subaccountId, destinationName,
                customResponseHandler);
    }

    @Override
    public void deleteDestinationOnTenantLevel(String subaccountId, String destinationName) {
        Request<String> deleteConfigurationOnApplicationLevelRequest = RequestBuilder
                .deleteRequest()
                .uri(buildRequestUri(DEFINED_CONFIGURATION_OAUTH_API_PATH_TENANT_LEVEL, subaccountId, destinationName))
                .build();

        StatusCodeHandler customStatusCodeHandler = DestinationConfigurationServiceStatusCodeHandler
                .create(subaccountId, destinationName, MANAGE_DESTINATION_SCOPE);

        execute(deleteConfigurationOnApplicationLevelRequest, customStatusCodeHandler);

        log.info(DELETING_CONFIGURATION_TENANT_LEVEL_WAS_SUCCESSFUL_MSG, destinationName, subaccountId);
    }

    @Override
    public void upsertDestinationOnApplicationLevel(String subaccountId, String applicationName,
            Destination destination) {
        DestinationDto destinationDto = destination.toDto();
        String destinationName = destination.getName();

        Request<MultipartEntity<DestinationDto>> upsertConfigurationOnApplicationLevelRequest = RequestBuilder
                .postRequest(DestinationDto.class)
                .multipartEntity(destinationName, destinationDto)
                .uri(buildRequestUri(OAUTH_API_PATH_APPLICATION_LEVEL, subaccountId, applicationName))
                .buildMultipart();

        StatusCodeHandler customStatusCodeHandler = DestinationConfigurationServiceStatusCodeHandler
                .create(subaccountId, applicationName, destinationName, MANAGE_DESTINATION_SCOPE);

        execute(upsertConfigurationOnApplicationLevelRequest, customStatusCodeHandler);

        log.info(UPSERTING_CONFIGURATION_APPLICATION_LEVEL_WAS_SUCCESSFUL_MSG, destination.getName(), applicationName,
                subaccountId);
    }

    @Override
    public Destination retrieveDestinationOnApplicationLevel(String subaccountId, String applicationName,
            String destinationName) {
        PropertiesResponseHandler<DestinationDto> customResponseHandler = new PropertiesResponseHandler<>(
                DestinationDto.class);
        return (Destination) retrieveDestinationOnApplicationLevel(subaccountId, applicationName,
                destinationName,
                customResponseHandler);
    }

    @Override
    public SAMLBearerDestination retrieveSAMLBearerDestinationOnApplicationLevel(
            String subaccountId, String applicationName, String destinationName) {
        PropertiesResponseHandler<SAMLBearerDestinationDto> customResponseHandler = new PropertiesResponseHandler<>(
                SAMLBearerDestinationDto.class);
        return (SAMLBearerDestination) retrieveDestinationOnApplicationLevel(subaccountId,
                applicationName, destinationName, customResponseHandler);
    }

    @Override
    public BasicDestination retrieveBasicDestinationOnApplicationLevel(String subaccountId, String applicationName,
            String destinationName) {
        PropertiesResponseHandler<BasicDestinationDto> customResponseHandler = new PropertiesResponseHandler<>(
                BasicDestinationDto.class);
        return (BasicDestination) retrieveDestinationOnApplicationLevel(subaccountId, applicationName, destinationName,
                customResponseHandler);
    }

    @Override
    public void deleteDestinationOnApplicationLevel(String subaccountId, String applicationName,
            String destinationName) {
        Request<String> deleteConfigurationOnApplicationLevelRequest = RequestBuilder
                .deleteRequest()
                .uri(buildRequestUri(DEFINED_CONFIGURATION_OAUTH_API_PATH_APPLICATION_LEVEL, subaccountId,
                        applicationName, destinationName))
                .build();

        StatusCodeHandler customStatusCodeHandler = DestinationConfigurationServiceStatusCodeHandler
                .create(subaccountId, applicationName, destinationName, MANAGE_DESTINATION_SCOPE);

        execute(deleteConfigurationOnApplicationLevelRequest, customStatusCodeHandler);

        log.info(DELETING_CONFIGURATION_APPLICATION_LEVEL_WAS_SUCCESSFUL_MSG, destinationName, applicationName,
                subaccountId);
    }

    @Override
    public void upsertDestinationOnSubscriptionLevel(String consumerSubaccountId, String providerSubaccountId,
            String applicationName, Destination destination) {
        DestinationDto destinationDto = destination.toDto();
        String destinationName = destination.getName();

        Request<MultipartEntity<DestinationDto>> upsertConfigurationOnSubscriptionLevelRequest = RequestBuilder
                .postRequest(DestinationDto.class)
                .multipartEntity(destinationName, destinationDto)
                .uri(buildRequestUri(OAUTH_API_PATH_SUBSCRIPTION_LEVEL, consumerSubaccountId, applicationName,
                        providerSubaccountId, applicationName))
                .buildMultipart();

        StatusCodeHandler customStatusCodeHandler = DestinationConfigurationServiceStatusCodeHandler
                .create(consumerSubaccountId, providerSubaccountId, applicationName, destinationName,
                        MANAGE_DESTINATION_SCOPE);

        execute(upsertConfigurationOnSubscriptionLevelRequest, customStatusCodeHandler);

        log.info(UPSERTING_CONFIGURATION_SUBSCRIPTION_LEVEL_WAS_SUCCESSFUL_MSG, destinationName, applicationName,
                providerSubaccountId, consumerSubaccountId);
    }

    @Override
    public Destination retrieveDestinationOnSubscriptionLevel(String consumerSubaccountId,
            String providerSubaccountId, String applicationName, String destinationName) {
        PropertiesResponseHandler<DestinationDto> customResponseHandler = new PropertiesResponseHandler<>(
                DestinationDto.class);
        return (Destination) retrieveDestinationOnSubscriptionLevel(consumerSubaccountId,
                providerSubaccountId, applicationName, destinationName, customResponseHandler);
    }

    @Override
    public SAMLBearerDestination retrieveSAMLBearerDestinationOnSubscriptionLevel(
            String consumerSubaccountId, String providerSubaccountId, String applicationName, String destinationName) {
        PropertiesResponseHandler<SAMLBearerDestinationDto> customResponseHandler = new PropertiesResponseHandler<>(
                SAMLBearerDestinationDto.class);
        return (SAMLBearerDestination) retrieveDestinationOnSubscriptionLevel(consumerSubaccountId,
                providerSubaccountId, applicationName, destinationName, customResponseHandler);
    }

    @Override
    public BasicDestination retrieveBasicDestinationOnSubscriptionLevel(String consumerSubaccountId,
            String providerSubaccountId, String applicationName, String destinationName) {
        PropertiesResponseHandler<BasicDestinationDto> customResponseHandler = new PropertiesResponseHandler<>(
                BasicDestinationDto.class);
        return (BasicDestination) retrieveDestinationOnSubscriptionLevel(consumerSubaccountId, providerSubaccountId,
                applicationName, destinationName, customResponseHandler);
    }

    @Override
    public void deleteDestinationOnSubscriptionLevel(String consumerSubaccountId, String providerSubaccountId,
            String applicationName, String destinationName) {
        Request<String> deleteConfigurationOnSubscriptionLevelRequest = RequestBuilder
                .deleteRequest()
                .uri(buildRequestUri(DEFINED_CONFIGURATION_OAUTH_API_PATH_SUBSCRIPTION_LEVEL, consumerSubaccountId,
                        applicationName, providerSubaccountId, applicationName, destinationName))
                .build();

        StatusCodeHandler customStatusCodeHandler = DestinationConfigurationServiceStatusCodeHandler
                .create(consumerSubaccountId, providerSubaccountId, applicationName, destinationName,
                        MANAGE_DESTINATION_SCOPE);

        execute(deleteConfigurationOnSubscriptionLevelRequest, customStatusCodeHandler);

        log.info(DELETING_CONFIGURATION_SUBSCRIPTION_LEVEL_WAS_SUCCESSFUL_MSG, destinationName, applicationName,
                providerSubaccountId, consumerSubaccountId);
    }

    private <T extends DestinationDto> Destination retrieveDestinationOnTenantLevel(
            String subaccountId, String destinationName, PropertiesResponseHandler<T> customResponseHandler) {
        Request<String> retrieveConfigurationOnApplicationLevelRequest = RequestBuilder
                .getRequest()
                .addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_OCTET_STREAM.toString())
                .uri(buildRequestUri(DEFINED_CONFIGURATION_OAUTH_API_PATH_TENANT_LEVEL, subaccountId,
                        destinationName))
                .build();

        StatusCodeHandler customStatusCodeHandler = DestinationConfigurationServiceStatusCodeHandler
                .create(subaccountId, destinationName, READ_DESTINATION_SCOPE);

        DestinationDto retrievedDestination = execute(retrieveConfigurationOnApplicationLevelRequest,
                customResponseHandler, customStatusCodeHandler).getEntity();

        log.info(RETRIEVING_CONFIGURATION_TENANT_LEVEL_WAS_SUCCESSFUL_MSG, destinationName, subaccountId);

        return retrievedDestination.toEntity();
    }

    private <T extends DestinationDto> Destination retrieveDestinationOnApplicationLevel(
            String subaccountId, String applicationName, String destinationName,
            PropertiesResponseHandler<T> customResponseHandler) {
        Request<String> retrieveConfigurationOnApplicationLevelRequest = RequestBuilder
                .getRequest()
                .addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_OCTET_STREAM.toString())
                .uri(buildRequestUri(DEFINED_CONFIGURATION_OAUTH_API_PATH_APPLICATION_LEVEL, subaccountId,
                        applicationName, destinationName))
                .build();

        StatusCodeHandler customStatusCodeHandler = DestinationConfigurationServiceStatusCodeHandler
                .create(subaccountId, applicationName, destinationName, READ_DESTINATION_SCOPE);

        DestinationDto retrievedDestination = execute(retrieveConfigurationOnApplicationLevelRequest,
                customResponseHandler, customStatusCodeHandler).getEntity();

        log.info(RETRIEVING_CONFIGURATION_APPLICATION_LEVEL_WAS_SUCCESSFUL_MSG, destinationName, applicationName,
                subaccountId);

        return retrievedDestination.toEntity();
    }

    private <T extends DestinationDto> Destination retrieveDestinationOnSubscriptionLevel(
            String consumerSubaccountId, String providerSubaccountId, String applicationName, String destinationName,
            PropertiesResponseHandler<T> customResponseHandler) {
        Request<String> retrieveConfigurationOnSubscriptionLevelRequest = RequestBuilder
                .getRequest()
                .uri(buildRequestUri(DEFINED_CONFIGURATION_OAUTH_API_PATH_SUBSCRIPTION_LEVEL, consumerSubaccountId,
                        applicationName, providerSubaccountId, applicationName, destinationName))
                .build();

        StatusCodeHandler customStatusCodeHandler = DestinationConfigurationServiceStatusCodeHandler
                .create(consumerSubaccountId, providerSubaccountId, applicationName, destinationName,
                        READ_DESTINATION_SCOPE);

        DestinationDto retrievedDestination = execute(retrieveConfigurationOnSubscriptionLevelRequest,
                customResponseHandler, customStatusCodeHandler).getEntity();

        log.info(RETRIEVING_CONFIGURATION_SUBSCRIPTION_LEVEL_WAS_SUCCESSFUL_MSG, destinationName, applicationName,
                providerSubaccountId, consumerSubaccountId);

        return retrievedDestination.toEntity();

    }

}
