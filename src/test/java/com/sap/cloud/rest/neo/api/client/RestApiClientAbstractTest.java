package com.sap.cloud.rest.neo.api.client;

import static com.sap.cloud.rest.neo.api.client.tests.utils.MockResponseUtil.makeMockedResponseWithStatusAndEntity;
import static com.sap.cloud.rest.neo.api.client.tests.utils.MockResponseUtil.makeMockedResponseWithStatusCode;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sap.cloud.rest.api.client.exceptions.ResponseException;
import com.sap.cloud.rest.api.client.exceptions.RestApiClientException;
import com.sap.cloud.rest.api.client.exceptions.UnauthorizedException;
import com.sap.cloud.rest.api.client.http.HttpClientProvider;
import com.sap.cloud.rest.neo.api.client.tests.utils.RequestMatcher;

public abstract class RestApiClientAbstractTest {

    protected static final String TEST_USER = "EXAMPLE_USER";
    protected static final String VALID_HOST = "https://example.com";
    protected static final String INVALID_HOST = "asd://example.com";

    protected HttpClient httpClient = Mockito.mock(HttpClient.class);
    protected HttpClientProvider httpClientProvider = Mockito.mock(HttpClientProvider.class);

    public abstract void initService();

    public abstract void executeSampleRequest() throws Exception;

    @Before
    public void createService() {
        when(httpClientProvider.createHttpClient(any())).thenReturn(httpClient);
        initService();
    }

    @Test(expected = RestApiClientException.class)
    public void testErrorHttpResponseCodeReturnedShouldThrowException() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(404);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        executeSampleRequest();

        verify(httpClient).execute(any());
    }

    @Test(expected = UnauthorizedException.class)
    public void testUnauthorizedStatusCodeReturnedShouldThrow() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(401);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        executeSampleRequest();

        verify(httpClient).execute(any());
    }

    @Test(expected = ResponseException.class)
    public void testUnexpectedErrorCodeReturnedShouldBeThrownAsErrorHttpResponseException() throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(409);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        executeSampleRequest();

        verify(httpClient).execute(any());
    }

    @Test(expected = RestApiClientException.class)
    public void testClientProtocolExceptionShouldBeThrownAsNeoApiClientException() throws Exception {
        when(httpClient.execute(any())).thenThrow(new ClientProtocolException());

        executeSampleRequest();

        verify(httpClient).execute(any());
    }

    @Test(expected = RestApiClientException.class)
    public void testIOExceptionShouldBeThrownAsNeoApiClientException() throws Exception {
        when(httpClient.execute(any())).thenThrow(new IOException());

        executeSampleRequest();

        verify(httpClient).execute(any());
    }

    @FunctionalInterface
    public interface ThrowingFunction {

        void run() throws RestApiClientException;

    }

    protected void verifyRequestMethodAndUrl(ThrowingFunction methodInvokation, String httpMethod, String url)
            throws Exception {
        verifyRequestMethodAndUrl(methodInvokation, httpMethod, url, 200);
    }

    protected void verifyRequestMethodAndUrl(ThrowingFunction methodInvokation, String httpMethod,
            String url, int mockedResponseCode) throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusCode(mockedResponseCode);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        methodInvokation.run();

        verify(httpClient).execute(argThat(new RequestMatcher(httpMethod, url)));
    }

    protected void verifyRequestMethodAndUrlAndRequestBody(ThrowingFunction methodInvokation,
            String httpMethod, String url, String responseText) throws Exception {
        verifyRequestMethodAndUrlAndRequestBody(methodInvokation, httpMethod, url, 200, responseText);
    }

    protected void verifyRequestMethodAndUrlAndRequestBody(ThrowingFunction methodInvokation,
            String httpMethod, String url, int mockedResponseCode, String responseText) throws Exception {
        HttpResponse mockedResponse = makeMockedResponseWithStatusAndEntity(mockedResponseCode, responseText);
        when(httpClient.execute(any())).thenReturn(mockedResponse);

        methodInvokation.run();

        verify(httpClient).execute(argThat(new RequestMatcher(httpMethod, url)));
    }
}
