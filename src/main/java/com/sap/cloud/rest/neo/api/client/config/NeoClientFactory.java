package com.sap.cloud.rest.neo.api.client.config;

import java.util.HashMap;
import java.util.Map;

import com.sap.cloud.rest.api.client.auth.Authentication;
import com.sap.cloud.rest.api.client.config.RestApiClientConfig;
import com.sap.cloud.rest.api.client.config.RestApiClientConfigBuilder;
import com.sap.cloud.rest.neo.api.client.config.auth.AuthenticationResolver;
import com.sap.cloud.rest.neo.api.client.config.auth.DefaultAuthenticationResolver;
import com.sap.cloud.rest.neo.api.client.config.host.AuthorizationManagementHostProvider;
import com.sap.cloud.rest.neo.api.client.config.host.ConfigApiHostProvider;
import com.sap.cloud.rest.neo.api.client.service.authorization.management.AuthorizationManagementRestApiClient;
import com.sap.cloud.rest.neo.api.client.service.configuration.DestinationConfigurationRestApiClient;
import com.sap.cloud.rest.neo.api.client.service.configuration.DestinationConfigurationService;

public class NeoClientFactory {

    private static final String MATCH_ALL_REGEX = ".*";

    private AuthenticationResolver authenticationResolver;
    private ConfigApiHostProvider configApiHostProvider;
    private AuthorizationManagementHostProvider authorizationManagementApiHostProvider;

    public NeoClientFactory(AuthenticationResolver authenticationResolver) {
        this.authenticationResolver = authenticationResolver;
        this.configApiHostProvider = new ConfigApiHostProvider();
        this.authorizationManagementApiHostProvider = new AuthorizationManagementHostProvider();
    }

    public NeoClientFactory(Authentication authentication) {
        Map<String, Authentication> authMappings = new HashMap<>();
        authMappings.put(MATCH_ALL_REGEX, authentication);
        this.authenticationResolver = new DefaultAuthenticationResolver(authMappings);
        this.configApiHostProvider = new ConfigApiHostProvider();
        this.authorizationManagementApiHostProvider = new AuthorizationManagementHostProvider();
    }

    public DestinationConfigurationService getDestinationConfigurationService(String regionAlias) {
        return new DestinationConfigurationRestApiClient(
                buildConfig(configApiHostProvider.getConfigApiHost(regionAlias)));
    }

    public AuthorizationManagementRestApiClient getAuthorizationManagementService(String regionAlias,
            String subaccount) {
        return new AuthorizationManagementRestApiClient(buildConfig(
                authorizationManagementApiHostProvider.getAuthorizationManagementHost(regionAlias, subaccount)));
    }

    private RestApiClientConfig buildConfig(String hostname) {
        RestApiClientConfigBuilder builder = RestApiClientConfigBuilder.getBuilder()
                .host(hostname)
                .authentication(authenticationResolver.getAuthentication(hostname));

        return builder.build();
    }
}