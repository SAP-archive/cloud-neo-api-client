package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.AUTHENTICATION_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.DESTINATION_TYPE_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.NAME_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.PROXY_TYPE_JSON_PROPERTY;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.URL_JSON_PROPERTY;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto;

public class Destination {

    private final String name;
    private final String url;
    private final AuthenticationType authenticationType;
    private final ProxyType proxyType;
    private final DestinationType destinationType;

    protected final Map<String, String> additionalProperties;

    public Destination(final String name, final String url, final AuthenticationType authenticationType,
            final ProxyType proxyType, final DestinationType destinationType,
            Map<String, String> additionalProperties) {
        this.name = name;
        this.url = url;
        this.authenticationType = authenticationType;
        this.proxyType = proxyType;
        this.destinationType = destinationType;
        this.additionalProperties = additionalProperties == null ? new HashMap<>() : additionalProperties;
    }

    public Destination(final String name, final String url, final AuthenticationType authenticationType,
            final ProxyType proxyType, final DestinationType destinationType) {
        this(name, url, authenticationType, proxyType, destinationType, null);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public ProxyType getProxyType() {
        return proxyType;
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public Map<String, String> getAdditionalProperties() {
        return new HashMap<String, String>(additionalProperties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destination that = (Destination) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(url, that.url) &&
                Objects.equals(additionalProperties, that.additionalProperties) &&
                authenticationType == that.authenticationType &&
                proxyType == that.proxyType &&
                destinationType == that.destinationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, authenticationType, proxyType, destinationType, additionalProperties);
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(Destination.class.getName(), ToStringStyle.JSON_STYLE)
                .append("name", name)
                .append("url", url)
                .append("authenticationType", authenticationType)
                .append("proxyType", proxyType)
                .append("destinationType", destinationType);

        additionalProperties.forEach((String key, String value) -> stringBuilder.append(key, value));

        return stringBuilder.toString();
    }

    public DestinationDto toDto() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(NAME_JSON_PROPERTY, getName());
        map.put(URL_JSON_PROPERTY, getUrl());
        map.put(AUTHENTICATION_JSON_PROPERTY, getAuthenticationType());
        map.put(PROXY_TYPE_JSON_PROPERTY, getProxyType());
        map.put(DESTINATION_TYPE_JSON_PROPERTY, getDestinationType());
        map.putAll(getAdditionalProperties());

        return new DestinationDto(map);
    }
}
