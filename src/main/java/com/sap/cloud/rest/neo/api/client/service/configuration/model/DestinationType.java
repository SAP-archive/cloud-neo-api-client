package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DestinationType {
    HTTP("HTTP"), RFC("RFC"), MAIL("MAIL"), LDAP("LDAP");

    private final String name;

    DestinationType(String name) {
        this.name = name;
    }

    public static DestinationType valueOfName(String name) {
        List<DestinationType> types = Arrays.asList(DestinationType.values());
        return types.stream()
                .filter(t -> t.name.equals(name))
                .findAny()
                .orElse(null);
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}