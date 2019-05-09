package com.sap.cloud.rest.neo.api.client.service.configuration.dto;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.AuthenticationType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.BasicDestination;

public class BasicDestinationDto extends DestinationDto {

    public static final String PASSWORD_JSON_PROPERTY = "Password";
    public static final String USER_JSON_PROPERTY = "User";

    @JsonCreator
    public BasicDestinationDto(Map<String, Object> properties) {
        super(properties);
    }

    public String getUser() {
        return (String) getProperty(USER_JSON_PROPERTY);
    }

    public char[] getPassword() {
        return getPropertyAsCharArray(PASSWORD_JSON_PROPERTY);
    }

    public Map<String, Object> getAdditionalProperties() {
        Map<String, Object> additionalProperties = super.getAdditionalProperties();
        additionalProperties.remove(USER_JSON_PROPERTY);
        additionalProperties.remove(PASSWORD_JSON_PROPERTY);

        return additionalProperties;
    }

    @Override
    protected ToStringBuilder getToStringBuilder() {
        return new ToStringBuilder(BasicDestinationDto.class.getName(), ToStringStyle.JSON_STYLE);
    }

    @Override
    public BasicDestination toEntity() {
        validateAuthenticationType(AuthenticationType.BASIC, getAuthentication());

        return new BasicDestination(
                getName(),
                getUrl(),
                getProxyType(),
                getDestinationType(),
                getUser(),
                getPassword(),
                valuesToString(getAdditionalProperties()));
    }

}
