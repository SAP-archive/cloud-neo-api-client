package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import static com.sap.cloud.rest.neo.api.client.tests.utils.JsonUtil.assertJsonEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;

import com.sap.cloud.rest.neo.api.client.service.configuration.dto.SAMLBearerDestinationDto;

import nl.jqno.equalsverifier.EqualsVerifier;

public class SAMLBearerDestinationTest {

    private static final String NAME = "oauth-destination-name";
    private static final String URL = "https://example.com";
    private static final AuthenticationType AUTHENTICATION_TYPE = AuthenticationType.SAML_BEARER;
    private static final ProxyType PROXY_TYPE = ProxyType.INTERNET;
    private static final DestinationType DESTINATION_TYPE = DestinationType.HTTP;
    private static final String AUDIENCE = "audience";
    private static final char[] CLIENT_KEY = "client-key".toCharArray();
    private static final String TOKEN_SERVICE_URL = "https://example.com";
    private static final String TOKEN_SERVICE_USER = "token-user";
    private static final char[] TOKEN_SERVICE_PASSWORD = "token-password".toCharArray();
    private static final String USER_ID_SOURCE = "user-id-source";
    private static final String NAME_ID_FORMAT = "name-id-format";
    private static final String AUTHN_CONTEXT_CLASS_REF = "authn-context-class-ref";

    private static final SAMLBearerDestination DESTINATION;

    private static final String TO_STRING = "{" +
            "\"name\":\"" + NAME + "\"," +
            "\"url\":\"" + URL + "\"," +
            "\"proxyType\":\"" + PROXY_TYPE + "\"," +
            "\"destinationType\":\"" + DESTINATION_TYPE + "\"," +
            "\"authenticationType\":\"" + AUTHENTICATION_TYPE + "\"," +
            "\"audience\":\"" + AUDIENCE + "\"," +
            "\"tokenServiceUrl\":\"" + TOKEN_SERVICE_URL + "\"," +
            "\"tokenServiceUser\":\"" + TOKEN_SERVICE_USER + "\"," +
            "\"userIdSource\":\"" + USER_ID_SOURCE + "\"," +
            "\"nameIdFormat\":\"" + NAME_ID_FORMAT + "\"," +
            "\"authnContextClassRef\":\"" + AUTHN_CONTEXT_CLASS_REF + "\"" +
            "}";

    static {
        HashMap<String, String> additionalProperties = new HashMap<>();
        additionalProperties.put("userIdSource", USER_ID_SOURCE);
        additionalProperties.put("nameIdFormat", NAME_ID_FORMAT);
        additionalProperties.put("authnContextClassRef", AUTHN_CONTEXT_CLASS_REF);

        DESTINATION = new SAMLBearerDestination(NAME, URL, PROXY_TYPE, AUDIENCE, CLIENT_KEY, TOKEN_SERVICE_URL,
                TOKEN_SERVICE_USER, TOKEN_SERVICE_PASSWORD, additionalProperties);
    }

    @Test
    public void getAudienceTest() {
        assertEquals(AUDIENCE, DESTINATION.getAudience());
    }

    @Test
    public void getClientKeyTest() {
        assertArrayEquals(CLIENT_KEY, DESTINATION.getClientKey());
    }

    @Test
    public void getTokenServiceURLTest() {
        assertEquals(TOKEN_SERVICE_URL, DESTINATION.getTokenServiceURL());
    }

    @Test
    public void getTokenServiceUserTest() {
        assertEquals(TOKEN_SERVICE_USER, DESTINATION.getTokenServiceUser());
    }

    @Test
    public void getTokenServicePasswordTest() {
        assertArrayEquals(TOKEN_SERVICE_PASSWORD, DESTINATION.getTokenServicePassword());
    }

    @Test
    public void getAdditionalPropertiesTest() {
        assertEquals(USER_ID_SOURCE, DESTINATION.getAdditionalProperties().get("userIdSource"));
        assertEquals(NAME_ID_FORMAT, DESTINATION.getAdditionalProperties().get("nameIdFormat"));
        assertEquals(AUTHN_CONTEXT_CLASS_REF, DESTINATION.getAdditionalProperties().get("authnContextClassRef"));
    }

    @Test
    public void noAdditionalPropertiesDestinationTest() {
        Destination destination = new SAMLBearerDestination(NAME, URL, PROXY_TYPE, AUDIENCE, CLIENT_KEY,
                TOKEN_SERVICE_URL, TOKEN_SERVICE_USER, TOKEN_SERVICE_PASSWORD);

        assertEquals(new HashMap<String, String>(), destination.getAdditionalProperties());
    }

    @Test
    public void nullPasswordsTest() {
        SAMLBearerDestination destination = new SAMLBearerDestination(NAME, URL, PROXY_TYPE, AUDIENCE, null,
                TOKEN_SERVICE_URL, TOKEN_SERVICE_USER, null);

        assertNull(destination.getClientKey());
        assertNull(destination.getTokenServicePassword());
    }

    @Test
    public void equalsTest() {
        EqualsVerifier.forClass(SAMLBearerDestination.class)
                .withRedefinedSuperclass()
                .verify();
    }

    @Test
    public void toStringTest() throws JSONException {
        assertJsonEquals(TO_STRING, DESTINATION.toString());
    }

    @Test
    public void toDtoTest() {
        SAMLBearerDestinationDto dto = DESTINATION.toDto();

        assertEquals(NAME, dto.getName());
        assertEquals(URL, dto.getUrl());
        assertEquals(AUTHENTICATION_TYPE, dto.getAuthentication());
        assertEquals(PROXY_TYPE, dto.getProxyType());
        assertEquals(DESTINATION_TYPE, dto.getDestinationType());
        assertEquals(TOKEN_SERVICE_URL, dto.getTokenServiceURL());
        assertEquals(TOKEN_SERVICE_USER, dto.getTokenServiceUser());
        assertArrayEquals(TOKEN_SERVICE_PASSWORD, dto.getTokenServicePassword());

        Map<String, Object> additioanalProperties = dto.getAdditionalProperties();
        assertEquals(USER_ID_SOURCE, additioanalProperties.get("userIdSource"));
        assertEquals(NAME_ID_FORMAT, additioanalProperties.get("nameIdFormat"));
        assertEquals(AUTHN_CONTEXT_CLASS_REF, additioanalProperties.get("authnContextClassRef"));
    }
}
