package com.sap.cloud.rest.neo.api.client.service.configuration;

import com.sap.cloud.rest.api.client.RequestBuilder;
import com.sap.cloud.rest.api.client.RestApiClient;
import com.sap.cloud.rest.api.client.config.RestApiClientConfig;
import com.sap.cloud.rest.api.client.handler.StatusCodeHandler;
import com.sap.cloud.rest.api.client.http.HttpClientProvider;
import com.sap.cloud.rest.api.client.model.Request;

public class DefaultRestApiClient extends RestApiClient {

    private static final String TEST_URI = "";
    public static final Request<String> REQUEST = RequestBuilder.getRequest().uri(TEST_URI).build();

    public DefaultRestApiClient(RestApiClientConfig config) {
        super(config);
    }

    public DefaultRestApiClient(RestApiClientConfig config, HttpClientProvider httpClientProvider) {
        super(config, httpClientProvider);
    }

    protected String getApiPath() {
        return "";
    }

    public void executeSampleRequest(StatusCodeHandler handler) {
        super.execute(REQUEST, handler);
    }
}
