package com.sap.cloud.rest.neo.api.client.service.configuration;

import static com.sap.cloud.rest.neo.api.client.service.configuration.DestinationConfigurationServiceStatusCodeHandler.DESTINATION_NOT_FOUND_ACCOUNT_LEVEL_MSG;
import static com.sap.cloud.rest.neo.api.client.service.configuration.DestinationConfigurationServiceStatusCodeHandler.DESTINATION_NOT_FOUND_MSG;
import static com.sap.cloud.rest.neo.api.client.service.configuration.DestinationConfigurationServiceStatusCodeHandler.PERMISSION_DENIED_MSG;
import static com.sap.cloud.rest.neo.api.client.service.configuration.DestinationConfigurationServiceStatusCodeHandler.SUBSCRIPTION_UNAUTHORIZED_MSG;
import static com.sap.cloud.rest.neo.api.client.service.configuration.DestinationConfigurationServiceStatusCodeHandler.UNAUTHORIZED_MSG;
import static com.sap.cloud.rest.neo.api.client.tests.utils.MockResponseUtil.makeMockedResponseWithStatusCode;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.slf4j.helpers.MessageFormatter.arrayFormat;
import static org.slf4j.helpers.MessageFormatter.format;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import com.sap.cloud.rest.api.client.config.RestApiClientConfig;
import com.sap.cloud.rest.api.client.config.RestApiClientConfigBuilder;
import com.sap.cloud.rest.api.client.exceptions.ResponseException;
import com.sap.cloud.rest.api.client.exceptions.UnauthorizedException;
import com.sap.cloud.rest.api.client.http.HttpClientProvider;
import com.sap.cloud.rest.api.client.model.HttpExchangeContext;
import com.sap.cloud.rest.api.client.model.Response;
import com.sap.cloud.rest.neo.api.client.service.configuration.exceptions.DestinationNotFoundException;
import com.sap.cloud.rest.neo.api.client.service.configuration.exceptions.PermissionDeniedException;

public class DestinationConfigurationServiceStatusCodeHandlerTest {

    private static final String TEST_HOST = "https://example.com";

    private static final String SUBACCOUNT_ID = "subaccountId";
    private static final String PROVIDER_SUBACCOUNT_ID = "providerSubaccountId";
    private static final String APPLICATION_NAME = "applicationName";
    private static final String CONFIGURATION_NAME = "configurationName";
    private static final String REQUIRED_SCOPE = "requiredScope";

    private static final DestinationConfigurationServiceStatusCodeHandler ACCOUNT_HANDLER = DestinationConfigurationServiceStatusCodeHandler
            .create(SUBACCOUNT_ID, CONFIGURATION_NAME, REQUIRED_SCOPE);

    private static final DestinationConfigurationServiceStatusCodeHandler APPLICATION_HANDLER = DestinationConfigurationServiceStatusCodeHandler
            .create(SUBACCOUNT_ID, APPLICATION_NAME, CONFIGURATION_NAME, REQUIRED_SCOPE);

    private static final DestinationConfigurationServiceStatusCodeHandler SUBSCRIPTION_HANDLER = DestinationConfigurationServiceStatusCodeHandler
            .create(SUBACCOUNT_ID, PROVIDER_SUBACCOUNT_ID, APPLICATION_NAME, CONFIGURATION_NAME, REQUIRED_SCOPE);

    private HttpClientProvider httpClientProvider = Mockito.mock(HttpClientProvider.class);
    private HttpClient httpClient = Mockito.mock(HttpClient.class);

    private DefaultRestApiClient mockClient;

    @Before
    public void createService() {
        when(httpClientProvider.createHttpClient(any())).thenReturn(httpClient);
        RestApiClientConfig config = RestApiClientConfigBuilder.getBuilder()
                .host(TEST_HOST)
                .build();

        mockClient = new DefaultRestApiClient(config, httpClientProvider);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void statusCodeHandlerTest_AccountLevel_Unauthorized() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(401);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        expectedException.expect(UnauthorizedException.class);
        expectedException.expectMessage(
                format(UNAUTHORIZED_MSG, SUBACCOUNT_ID, getExchangeContext(mockedResponse)).getMessage());

        mockClient.executeSampleRequest(ACCOUNT_HANDLER);

        verify(httpClient).execute(any());
    }

    @Test
    public void statusCodeHandlerTest_ApplicationLevel_Unauthorized() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(401);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        expectedException.expect(UnauthorizedException.class);
        expectedException.expectMessage(
                format(UNAUTHORIZED_MSG, SUBACCOUNT_ID, getExchangeContext(mockedResponse)).getMessage());

        mockClient.executeSampleRequest(APPLICATION_HANDLER);

        verify(httpClient).execute(any());
    }

    @Test
    public void statusCodeHandlerTest_SubscriptionLevel_Unauthorized() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(401);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        expectedException.expect(UnauthorizedException.class);
        expectedException.expectMessage(
                arrayFormat(SUBSCRIPTION_UNAUTHORIZED_MSG, new Object[] { SUBACCOUNT_ID, PROVIDER_SUBACCOUNT_ID,
                        PROVIDER_SUBACCOUNT_ID, APPLICATION_NAME, getExchangeContext(mockedResponse) }).getMessage());

        mockClient.executeSampleRequest(SUBSCRIPTION_HANDLER);

        verify(httpClient).execute(any());
    }

    @Test
    public void statusCodeHandlerTest_AccountLevel_Forbidden() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(403);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        expectedException.expect(PermissionDeniedException.class);
        expectedException.expectMessage(
                format(PERMISSION_DENIED_MSG, REQUIRED_SCOPE, getExchangeContext(mockedResponse)).getMessage());

        mockClient.executeSampleRequest(ACCOUNT_HANDLER);

        verify(httpClient).execute(any());
    }

    @Test
    public void statusCodeHandlerTest_ApplicationLevel_Forbidden() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(403);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        expectedException.expect(PermissionDeniedException.class);
        expectedException.expectMessage(
                format(PERMISSION_DENIED_MSG, REQUIRED_SCOPE, getExchangeContext(mockedResponse)).getMessage());

        mockClient.executeSampleRequest(APPLICATION_HANDLER);

        verify(httpClient).execute(any());
    }

    @Test
    public void statusCodeHandlerTest_SubscriptionLevel_Forbidden() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(403);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        expectedException.expect(PermissionDeniedException.class);
        expectedException.expectMessage(
                format(PERMISSION_DENIED_MSG, REQUIRED_SCOPE, getExchangeContext(mockedResponse)).getMessage());

        mockClient.executeSampleRequest(SUBSCRIPTION_HANDLER);

        verify(httpClient).execute(any());
    }

    @Test
    public void statusCodeHandlerTest_AccountLevel_NotFound() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(404);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        expectedException.expect(DestinationNotFoundException.class);
        expectedException.expectMessage(arrayFormat(DESTINATION_NOT_FOUND_ACCOUNT_LEVEL_MSG,
                new Object[] { CONFIGURATION_NAME, SUBACCOUNT_ID,
                        getExchangeContext(mockedResponse) }).getMessage());

        mockClient.executeSampleRequest(ACCOUNT_HANDLER);

        verify(httpClient).execute(any());
    }

    @Test
    public void statusCodeHandlerTest_ApplicationLevel_NotFound() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(404);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        expectedException.expect(DestinationNotFoundException.class);
        expectedException.expectMessage(arrayFormat(DESTINATION_NOT_FOUND_MSG,
                new Object[] { CONFIGURATION_NAME, APPLICATION_NAME, SUBACCOUNT_ID,
                        getExchangeContext(mockedResponse) }).getMessage());

        mockClient.executeSampleRequest(APPLICATION_HANDLER);

        verify(httpClient).execute(any());
    }

    @Test
    public void statusCodeHandlerTest_SubacriptionLevel_NotFound() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(404);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        expectedException.expect(DestinationNotFoundException.class);
        expectedException.expectMessage(arrayFormat(DESTINATION_NOT_FOUND_MSG,
                new Object[] { CONFIGURATION_NAME, APPLICATION_NAME, SUBACCOUNT_ID,
                        getExchangeContext(mockedResponse) }).getMessage());

        mockClient.executeSampleRequest(SUBSCRIPTION_HANDLER);

        verify(httpClient).execute(any());
    }

    @Test
    public void statusCodeHandlerTest_DefaultStatus() throws Exception {
        expectedException.expect(ResponseException.class);

        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(500);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        mockClient.executeSampleRequest(APPLICATION_HANDLER);

        verify(httpClient).execute(any());
    }

    private HttpExchangeContext getExchangeContext(HttpResponse mockedResponse) {
        return new HttpExchangeContext(DefaultRestApiClient.REQUEST, new Response<String>(mockedResponse, ""));
    }
}