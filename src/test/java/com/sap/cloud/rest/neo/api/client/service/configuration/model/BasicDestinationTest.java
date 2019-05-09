package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import static com.sap.cloud.rest.neo.api.client.tests.utils.JsonUtil.assertJsonEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;

import com.sap.cloud.rest.neo.api.client.service.configuration.dto.BasicDestinationDto;

import nl.jqno.equalsverifier.EqualsVerifier;

public class BasicDestinationTest {

    private static final String NAME = "basic-destination-name";
    private static final String URL = "https://example.com";
    private static final AuthenticationType AUTHENTICATION_TYPE = AuthenticationType.BASIC;
    private static final ProxyType PROXY_TYPE = ProxyType.INTERNET;
    private static final DestinationType DESTINATION_TYPE = DestinationType.HTTP;
    private static final String USER = "test-user";
    private static final char[] PASSWORD = "my-pass".toCharArray();
    private static final String USER_ID_SOURCE = "user-id-source";
    private static final String NAME_ID_FORMAT = "name-id-format";
    private static final String AUTHN_CONTEXT_CLASS_REF = "authn-context-class-ref";

    private static final BasicDestination DESTINATION;

    private static final String TO_STRING = "{" +
            "\"name\":\"" + NAME + "\"," +
            "\"url\":\"" + URL + "\"," +
            "\"proxyType\":\"" + PROXY_TYPE + "\"," +
            "\"destinationType\":\"" + DESTINATION_TYPE + "\"," +
            "\"authenticationType\":\"" + AUTHENTICATION_TYPE + "\"," +
            "\"user\":\"" + USER + "\"," +
            "\"userIdSource\":\"" + USER_ID_SOURCE + "\"," +
            "\"nameIdFormat\":\"" + NAME_ID_FORMAT + "\"," +
            "\"authnContextClassRef\":\"" + AUTHN_CONTEXT_CLASS_REF + "\"" +
            "}";

    static {
        HashMap<String, String> additionalProperties = new HashMap<>();
        additionalProperties.put("userIdSource", USER_ID_SOURCE);
        additionalProperties.put("nameIdFormat", NAME_ID_FORMAT);
        additionalProperties.put("authnContextClassRef", AUTHN_CONTEXT_CLASS_REF);

        DESTINATION = new BasicDestination(NAME,
                URL, PROXY_TYPE, DESTINATION_TYPE, USER, PASSWORD, additionalProperties);
    }

    @Test
    public void getUserTest() {
        assertEquals(USER, DESTINATION.getUser());
    }

    @Test
    public void getPasswordTest() {
        assertArrayEquals(PASSWORD, DESTINATION.getPassword());
    }

    @Test
    public void getAdditionalPropertiesTest() {
        assertEquals(USER_ID_SOURCE, DESTINATION.getAdditionalProperties().get("userIdSource"));
        assertEquals(NAME_ID_FORMAT, DESTINATION.getAdditionalProperties().get("nameIdFormat"));
        assertEquals(AUTHN_CONTEXT_CLASS_REF, DESTINATION.getAdditionalProperties().get("authnContextClassRef"));
    }

    @Test
    public void noAdditionalPropertiesDestinationTest() {
        Destination destination = new BasicDestination(NAME, URL, PROXY_TYPE, DESTINATION_TYPE, USER, PASSWORD);

        assertEquals(new HashMap<String, String>(), destination.getAdditionalProperties());
    }

    @Test
    public void nullPasswordsTest() {
        BasicDestination destination = new BasicDestination(NAME, URL, PROXY_TYPE, DESTINATION_TYPE, USER, null);

        assertNull(destination.getPassword());
    }

    @Test
    public void equalsTest() {
        EqualsVerifier.forClass(BasicDestination.class)
                .withRedefinedSuperclass()
                .verify();
    }

    @Test
    public void toStringTest() throws JSONException {
        assertJsonEquals(TO_STRING, DESTINATION.toString());
    }

    @Test
    public void toDtoTest() {
        BasicDestinationDto dto = DESTINATION.toDto();

        assertEquals(NAME, dto.getName());
        assertEquals(URL, dto.getUrl());
        assertEquals(AUTHENTICATION_TYPE, dto.getAuthentication());
        assertEquals(PROXY_TYPE, dto.getProxyType());
        assertEquals(DESTINATION_TYPE, dto.getDestinationType());
        assertEquals(USER, dto.getUser());
        assertArrayEquals(PASSWORD, dto.getPassword());

        Map<String, Object> additioanalProperties = dto.getAdditionalProperties();
        assertEquals(USER_ID_SOURCE, additioanalProperties.get("userIdSource"));
        assertEquals(NAME_ID_FORMAT, additioanalProperties.get("nameIdFormat"));
        assertEquals(AUTHN_CONTEXT_CLASS_REF, additioanalProperties.get("authnContextClassRef"));
    }
}
