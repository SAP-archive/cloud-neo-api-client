package com.sap.cloud.rest.neo.api.client.service.configuration.dto;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.AuthenticationType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.SAMLBearerDestination;

public class SAMLBearerDestinationDto extends DestinationDto {

    public static final String AUDIENCE_JSON_PROPERTY = "audience";
    public static final String CLIENT_KEY_JSON_PROPERTY = "clientKey";
    public static final String TOKEN_SERVICE_URL_JSON_PROPERTY = "tokenServiceURL";
    public static final String TOKEN_SERVICE_USER_JSON_PROPERTY = "tokenServiceUser";
    public static final String TOKEN_SERVICE_PASSWORD_JSON_PROPERTY = "tokenServicePassword";

    @JsonCreator
    public SAMLBearerDestinationDto(Map<String, Object> properties) {
        super(properties);
    }

    public String getAudience() {
        return (String) getProperty(AUDIENCE_JSON_PROPERTY);
    }

    public char[] getClientKey() {
        return getPropertyAsCharArray(CLIENT_KEY_JSON_PROPERTY);
    }

    public String getTokenServiceURL() {
        return (String) getProperty(TOKEN_SERVICE_URL_JSON_PROPERTY);
    }

    public String getTokenServiceUser() {
        return (String) getProperty(TOKEN_SERVICE_USER_JSON_PROPERTY);
    }

    public char[] getTokenServicePassword() {
        return getPropertyAsCharArray(TOKEN_SERVICE_PASSWORD_JSON_PROPERTY);
    }

    public Map<String, Object> getAdditionalProperties() {
        Map<String, Object> additionalProperties = super.getAdditionalProperties();
        additionalProperties.remove(AUDIENCE_JSON_PROPERTY);
        additionalProperties.remove(CLIENT_KEY_JSON_PROPERTY);
        additionalProperties.remove(TOKEN_SERVICE_URL_JSON_PROPERTY);
        additionalProperties.remove(TOKEN_SERVICE_USER_JSON_PROPERTY);
        additionalProperties.remove(TOKEN_SERVICE_PASSWORD_JSON_PROPERTY);

        return additionalProperties;
    }

    @Override
    protected ToStringBuilder getToStringBuilder() {
        return new ToStringBuilder(SAMLBearerDestinationDto.class.getName(), ToStringStyle.JSON_STYLE);
    }

    @Override
    public SAMLBearerDestination toEntity() {
        validateAuthenticationType(AuthenticationType.SAML_BEARER, getAuthentication());

        return new SAMLBearerDestination(
                getName(),
                getUrl(),
                getProxyType(),
                getAudience(),
                getClientKey(),
                getTokenServiceURL(),
                getTokenServiceUser(),
                getTokenServicePassword(),
                valuesToString(getAdditionalProperties()));
    }

}
