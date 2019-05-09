package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AuthenticationType {
    CLIENT_CERT("ClientCertificateAuthentication"), BASIC("BasicAuthentication"), SAML_BEARER(
            "OAuth2SAMLBearerAssertion"), NO_AUTH("NoAuthentication");

    private final String name;

    AuthenticationType(String name) {
        this.name = name;
    }

    public static AuthenticationType valueOfName(String name) {
        List<AuthenticationType> types = Arrays.asList(AuthenticationType.values());
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