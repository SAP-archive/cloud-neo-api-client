package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.BasicDestinationDto.PASSWORD_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.BasicDestinationDto.USER_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.AUTHENTICATION_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.DESTINATION_TYPE_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.NAME_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.PROXY_TYPE_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.URL_JSON_PROPERTY;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.sap.cloud.rest.neo.api.client.service.configuration.dto.BasicDestinationDto;

public final class BasicDestination extends Destination {

    private final String user;
    private final char[] password;

    public BasicDestination(String name, String url, ProxyType proxyType, DestinationType destinationType, String user,
            char[] password, Map<String, String> additionalProperties) {
        super(name, url, AuthenticationType.BASIC, proxyType, destinationType, additionalProperties);
        this.user = user;
        this.password = password;
    }

    public BasicDestination(String name, String url, ProxyType proxyType, DestinationType destinationType, String user,
            char[] password) {
        this(name, url, proxyType, destinationType, user, password, null);
    }

    public String getUser() {
        return user;
    }

    public char[] getPassword() {
        if (password != null) {
            return password.clone();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o))
            return false;
        BasicDestination that = (BasicDestination) o;
        return Objects.equals(user, that.user) && Arrays.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, Arrays.hashCode(password));
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(BasicDestination.class.getName(), ToStringStyle.JSON_STYLE)
                .append("name", getName())
                .append("url", getUrl())
                .append("proxyType", getProxyType())
                .append("destinationType", getDestinationType())
                .append("authenticationType", getAuthenticationType())
                .append("user", user);

        additionalProperties.forEach((String key, String value) -> stringBuilder.append(key, value));

        return stringBuilder.toString();
    }

    @Override
    public BasicDestinationDto toDto() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(NAME_JSON_PROPERTY, getName());
        map.put(URL_JSON_PROPERTY, getUrl());
        map.put(AUTHENTICATION_JSON_PROPERTY, getAuthenticationType());
        map.put(PROXY_TYPE_JSON_PROPERTY, getProxyType());
        map.put(DESTINATION_TYPE_JSON_PROPERTY, getDestinationType());
        map.put(USER_JSON_PROPERTY, getUser());
        map.put(PASSWORD_JSON_PROPERTY, getPassword());
        map.putAll(getAdditionalProperties());

        return new BasicDestinationDto(map);
    }

}
