package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.AUTHENTICATION_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.DESTINATION_TYPE_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.NAME_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.PROXY_TYPE_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.URL_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.SAMLBearerDestinationDto.AUDIENCE_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.SAMLBearerDestinationDto.CLIENT_KEY_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.SAMLBearerDestinationDto.TOKEN_SERVICE_PASSWORD_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.SAMLBearerDestinationDto.TOKEN_SERVICE_URL_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.SAMLBearerDestinationDto.TOKEN_SERVICE_USER_JSON_PROPERTY;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.sap.cloud.rest.neo.api.client.service.configuration.dto.SAMLBearerDestinationDto;

public final class SAMLBearerDestination extends Destination {

    private final String audience;
    private final char[] clientKey;
    private final String tokenServiceURL;
    private final String tokenServiceUser;
    private final char[] tokenServicePassword;

    public SAMLBearerDestination(String name, String url, ProxyType proxyType, String audience,
            char[] clientKey, String tokenServiceURL, String tokenServiceUser, char[] tokenServicePassword,
            Map<String, String> additionalProperties) {
        super(name, url, AuthenticationType.SAML_BEARER, proxyType, DestinationType.HTTP, additionalProperties);
        this.audience = audience;
        this.clientKey = clientKey;
        this.tokenServiceURL = tokenServiceURL;
        this.tokenServiceUser = tokenServiceUser;
        this.tokenServicePassword = tokenServicePassword;
    }

    public SAMLBearerDestination(String name, String url, ProxyType proxyType, String audience,
            char[] clientKey, String tokenServiceURL, String tokenServiceUser, char[] tokenServicePassword) {
        this(name, url, proxyType, audience, clientKey, tokenServiceURL, tokenServiceUser, tokenServicePassword, null);
    }

    public String getAudience() {
        return audience;
    }

    public char[] getClientKey() {
        if (clientKey != null) {
            return clientKey.clone();
        }
        return null;
    }

    public String getTokenServiceURL() {
        return tokenServiceURL;
    }

    public String getTokenServiceUser() {
        return tokenServiceUser;
    }

    public char[] getTokenServicePassword() {
        if (tokenServicePassword != null) {
            return tokenServicePassword.clone();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SAMLBearerDestination that = (SAMLBearerDestination) o;
        return Objects.equals(audience, that.audience) &&
                Arrays.equals(clientKey, that.clientKey) &&
                Objects.equals(tokenServiceURL, that.tokenServiceURL) &&
                Objects.equals(tokenServiceUser, that.tokenServiceUser) &&
                Arrays.equals(tokenServicePassword, that.tokenServicePassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), audience, Arrays.hashCode(clientKey), tokenServiceURL, tokenServiceUser,
                Arrays.hashCode(tokenServicePassword));
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(SAMLBearerDestination.class.getName(),
                ToStringStyle.JSON_STYLE)
                        .append("name", getName())
                        .append("url", getUrl())
                        .append("proxyType", getProxyType())
                        .append("destinationType", getDestinationType())
                        .append("authenticationType", getAuthenticationType())
                        .append("audience", audience)
                        .append("tokenServiceUrl", tokenServiceURL)
                        .append("tokenServiceUser", tokenServiceUser);

        additionalProperties.forEach((String key, String value) -> stringBuilder.append(key, value));

        return stringBuilder.toString();
    }

    @Override
    public SAMLBearerDestinationDto toDto() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(NAME_JSON_PROPERTY, getName());
        map.put(URL_JSON_PROPERTY, getUrl());
        map.put(AUTHENTICATION_JSON_PROPERTY, getAuthenticationType());
        map.put(PROXY_TYPE_JSON_PROPERTY, getProxyType());
        map.put(DESTINATION_TYPE_JSON_PROPERTY, getDestinationType());
        map.put(AUDIENCE_JSON_PROPERTY, getAudience());
        map.put(CLIENT_KEY_JSON_PROPERTY, getClientKey());
        map.put(TOKEN_SERVICE_URL_JSON_PROPERTY, getTokenServiceURL());
        map.put(TOKEN_SERVICE_USER_JSON_PROPERTY, getTokenServiceUser());
        map.put(TOKEN_SERVICE_PASSWORD_JSON_PROPERTY, getTokenServicePassword());
        map.putAll(getAdditionalProperties());

        return new SAMLBearerDestinationDto(map);
    }
}
