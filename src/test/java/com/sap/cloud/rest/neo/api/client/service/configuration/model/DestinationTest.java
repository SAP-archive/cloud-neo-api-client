package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto;

import nl.jqno.equalsverifier.EqualsVerifier;

public class DestinationTest {

    private static final String NAME = "destination-name";
    private static final String URL = "destination-url";
    private static final AuthenticationType AUTHENTICATION_TYPE = AuthenticationType.SAML_BEARER;
    private static final ProxyType PROXY_TYPE = ProxyType.INTERNET;
    private static final DestinationType DESTINATION_TYPE = DestinationType.HTTP;

    private static final Destination DESTINATION = new Destination(NAME, URL,
            AUTHENTICATION_TYPE, PROXY_TYPE, DESTINATION_TYPE);

    private static final String TO_STRING = "{" +
            "\"name\":\"" + NAME + "\"," +
            "\"url\":\"" + URL + "\"," +
            "\"authenticationType\":\"" + AUTHENTICATION_TYPE + "\"," +
            "\"proxyType\":\"" + PROXY_TYPE + "\"," +
            "\"destinationType\":\"" + DESTINATION_TYPE + "\"" +
            "}";

    @Test
    public void getNameTest() {
        assertEquals(NAME, DESTINATION.getName());
    }

    @Test
    public void getUrlTest() {
        assertEquals(URL, DESTINATION.getUrl());
    }

    @Test
    public void getAuthenticationTypeTest() {
        assertEquals(AUTHENTICATION_TYPE, DESTINATION.getAuthenticationType());
    }

    @Test
    public void getProxyTypeTest() {
        assertEquals(PROXY_TYPE, DESTINATION.getProxyType());
    }

    @Test
    public void getDestinationType() {
        assertEquals(DESTINATION_TYPE, DESTINATION.getDestinationType());
    }

    @Test
    public void equalsTest() {
        EqualsVerifier.forClass(Destination.class)
                .usingGetClass().verify();
    }

    @Test
    public void toStringTest() {
        assertEquals(TO_STRING, DESTINATION.toString());
    }

    @Test
    public void toDtoTest() {
        DestinationDto dto = DESTINATION.toDto();

        assertEquals(NAME, dto.getName());
        assertEquals(URL, dto.getUrl());
        assertEquals(AUTHENTICATION_TYPE, dto.getAuthentication());
        assertEquals(PROXY_TYPE, dto.getProxyType());
        assertEquals(DESTINATION_TYPE, dto.getDestinationType());
    }
}