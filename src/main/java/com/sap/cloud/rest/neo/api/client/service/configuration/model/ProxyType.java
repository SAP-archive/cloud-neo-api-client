package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProxyType {
    INTERNET("Internet"), ON_PREMISE("OnPremise");

    private final String name;

    ProxyType(String name) {
        this.name = name;
    }

    public static ProxyType valueOfName(String name) {
        List<ProxyType> types = Arrays.asList(ProxyType.values());
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
