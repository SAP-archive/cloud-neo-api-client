package com.sap.cloud.rest.neo.api.client.service.configuration.dto;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.AuthenticationType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.Destination;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.DestinationType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.ProxyType;

public class DestinationDto {

    static final String DESTINATION_AUTHENTICATION_TYPE_IS_NOT_VALID_MSG = "Destination authentication type is not valid. Expected: [{0}] Actual: [{1}]";

    public static final String NAME_JSON_PROPERTY = "Name";
    public static final String URL_JSON_PROPERTY = "URL";
    public static final String AUTHENTICATION_JSON_PROPERTY = "Authentication";
    public static final String PROXY_TYPE_JSON_PROPERTY = "ProxyType";
    public static final String DESTINATION_TYPE_JSON_PROPERTY = "Type";
    private static final String KEY_VALUE = "clientkey";
    private static final String PASS_VALUE = "pass";
    private static final String SECRET_VALUE = "secret";
    static final String SECURED_VALUE = "*******";
    protected final Map<String, Object> properties;

    @JsonCreator
    public DestinationDto(Map<String, Object> properties) {
        this.properties = properties;
    }

    public String getName() {
        return (String) properties.get(NAME_JSON_PROPERTY);
    }

    public String getUrl() {
        return (String) properties.get(URL_JSON_PROPERTY);
    }

    public AuthenticationType getAuthentication() {
        Object authenticationType = properties.get(AUTHENTICATION_JSON_PROPERTY);
        return authenticationType instanceof AuthenticationType
                ? (AuthenticationType) authenticationType
                : AuthenticationType.valueOfName((String) authenticationType);
    }

    public ProxyType getProxyType() {
        Object proxyType = properties.get(PROXY_TYPE_JSON_PROPERTY);
        return proxyType instanceof ProxyType
                ? (ProxyType) proxyType
                : ProxyType.valueOfName((String) proxyType);
    }

    public DestinationType getDestinationType() {
        Object destinationType = properties.get(DESTINATION_TYPE_JSON_PROPERTY);
        return destinationType instanceof DestinationType
                ? (DestinationType) destinationType
                : DestinationType.valueOfName((String) destinationType);
    }

    public Map<String, Object> getAdditionalProperties() {
        Map<String, Object> additionalProperties = new HashMap<>(properties);
        additionalProperties.remove(NAME_JSON_PROPERTY);
        additionalProperties.remove(URL_JSON_PROPERTY);
        additionalProperties.remove(AUTHENTICATION_JSON_PROPERTY);
        additionalProperties.remove(PROXY_TYPE_JSON_PROPERTY);
        additionalProperties.remove(DESTINATION_TYPE_JSON_PROPERTY);

        return additionalProperties;
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    @JsonValue
    public Map<String, Object> getProperties() {
        return new HashMap<>(properties);
    }
    
    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof DestinationDto))
            return false;
        DestinationDto other = (DestinationDto) obj;
        if (properties == null)
            return other.properties == null;
        if (other.properties == null)
            return false;
        Iterator<Entry<String, Object>> iterator = properties.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Object> thisObj = iterator.next();
            String thisKey = thisObj.getKey();
            Object thisValue = thisObj.getValue();
            Object otherValue = other.properties.get(thisKey);
            if (thisValue == null) {
                if (otherValue != null || !other.properties.containsKey(thisKey))
                    return false;
            } else if (otherValue == null) {
                return false;
            } else {
                if (thisValue instanceof char[] || otherValue instanceof char[]) {
                    if (!Arrays.equals(getValueAsCharArray(thisValue), getValueAsCharArray(otherValue)))
                        return false;
                } else if (thisValue.getClass().isEnum() || otherValue.getClass().isEnum()) {
                    if (!thisValue.toString().equals(otherValue.toString()))
                        return false;
                } else if (!thisValue.equals(otherValue)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((properties == null) ? 0 : properties.hashCode());
        return result;
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = getToStringBuilder();

        properties.forEach((String key, Object value) -> stringBuilder.append(key, hidePasswords(key, value)));

        return stringBuilder.toString();
    }

    protected ToStringBuilder getToStringBuilder() {
        return new ToStringBuilder(DestinationDto.class.getName(), ToStringStyle.JSON_STYLE);
    }

    private Object hidePasswords(String key, Object value) {
        key = key.toLowerCase();
        if (key.contains(SECRET_VALUE) || key.contains(PASS_VALUE) || key.contains(KEY_VALUE)) {
            return SECURED_VALUE;
        }

        return value;
    }

    public Destination toEntity() {
        return new Destination(
                getName(),
                getUrl(),
                getAuthentication(),
                getProxyType(),
                getDestinationType(),
                valuesToString(getAdditionalProperties()));
    }

    protected Map<String, String> valuesToString(Map<String, Object> additionalProperties) {
        return additionalProperties
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> String.valueOf(e.getValue())));
    }

    protected void validateAuthenticationType(AuthenticationType expected, AuthenticationType actual) {
        if (!expected.equals(actual)) {
            throw new InvalidAuthenticationException(
                    MessageFormat.format(DESTINATION_AUTHENTICATION_TYPE_IS_NOT_VALID_MSG,
                            expected, actual));
        }
    }

    protected char[] getPropertyAsCharArray(String key) {
        Object property = getProperty(key);
        return getValueAsCharArray(property);
    }

    private char[] getValueAsCharArray(Object value) {
        if (value == null) {
            return new char[0];
        }
        if (value instanceof char[]) {
            return (char[]) value;
        }
        return String.valueOf(value).toCharArray();
    }

}
