package com.sap.cloud.rest.neo.api.client.service.configuration.dto;

import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.DESTINATION_AUTHENTICATION_TYPE_IS_NOT_VALID_MSG;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDto.SECURED_VALUE;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDtoTest.DESTINATION_TYPE;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDtoTest.NAME;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDtoTest.PROPERTIES;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDtoTest.PROXY_TYPE;
import static com.sap.cloud.rest.neo.api.client.service.configuration.dto.DestinationDtoTest.URL;
import static com.sap.cloud.rest.neo.api.client.tests.utils.JsonUtil.assertJsonEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.rest.neo.api.client.AbstractDtoTest;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.AuthenticationType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.SAMLBearerDestination;

import nl.jqno.equalsverifier.EqualsVerifier;

public class SAMLBearerDestinationDtoTest extends AbstractDtoTest {

    private static final String AUDIENCE = "audience";
    private static final char[] CLIENT_KEY = "client-key".toCharArray();
    private static final String TOKEN_SERVICE_URL = "https://example.com";
    private static final String TOKEN_SERVICE_USER = "token-user";
    private static final char[] TOKEN_SERVICE_PASSWORD = "token-password".toCharArray();
    private static final String USER_ID_SOURCE = "user-id-source";
    private static final String NAME_ID_FORMAT = "name-id-format";
    private static final String AUTHN_CONTEXT_CLASS_REF = "authn-context-class-ref";
    private static final AuthenticationType AUTHENTICATION = AuthenticationType.SAML_BEARER;
    private static final AuthenticationType INVALID_AUTHENTICATION = AuthenticationType.BASIC;

    private static final String JSON = "{" +
            "\"Name\":\"" + NAME + "\"," +
            "\"URL\":\"" + URL + "\"," +
            "\"Authentication\":\"" + AUTHENTICATION + "\"," +
            "\"ProxyType\":\"" + PROXY_TYPE + "\"," +
            "\"Type\":\"" + DESTINATION_TYPE + "\"," +
            "\"audience\":\"" + AUDIENCE + "\"," +
            "\"clientKey\":\"" + String.valueOf(CLIENT_KEY) + "\"," +
            "\"tokenServiceURL\":\"" + TOKEN_SERVICE_URL + "\"," +
            "\"tokenServiceUser\":\"" + TOKEN_SERVICE_USER + "\"," +
            "\"tokenServicePassword\":\"" + String.valueOf(TOKEN_SERVICE_PASSWORD) + "\"," +
            "\"userIdSource\":\"" + USER_ID_SOURCE + "\"," +
            "\"nameIdFormat\":\"" + NAME_ID_FORMAT + "\"," +
            "\"authnContextClassRef\":\"" + AUTHN_CONTEXT_CLASS_REF + "\"" +
            "}";

    private static final String JSON_WITHOUT_PASSWORDS = "{" +
            "\"Name\":\"" + NAME + "\"," +
            "\"URL\":\"" + URL + "\"," +
            "\"Authentication\":\"" + AUTHENTICATION + "\"," +
            "\"ProxyType\":\"" + PROXY_TYPE + "\"," +
            "\"Type\":\"" + DESTINATION_TYPE + "\"," +
            "\"audience\":\"" + AUDIENCE + "\"," +
            "\"tokenServiceURL\":\"" + TOKEN_SERVICE_URL + "\"," +
            "\"tokenServiceUser\":\"" + TOKEN_SERVICE_USER + "\"," +
            "\"userIdSource\":\"" + USER_ID_SOURCE + "\"," +
            "\"nameIdFormat\":\"" + NAME_ID_FORMAT + "\"," +
            "\"authnContextClassRef\":\"" + AUTHN_CONTEXT_CLASS_REF + "\"" +
            "}";

    private static final String TO_STRING = "{" +
            "\"Name\":\"" + NAME + "\"," +
            "\"URL\":\"" + URL + "\"," +
            "\"Authentication\":\"" + AUTHENTICATION + "\"," +
            "\"ProxyType\":\"" + PROXY_TYPE + "\"," +
            "\"Type\":\"" + DESTINATION_TYPE + "\"," +
            "\"audience\":\"" + AUDIENCE + "\"," +
            "\"clientKey\":\"" + SECURED_VALUE + "\"," +
            "\"tokenServiceURL\":\"" + TOKEN_SERVICE_URL + "\"," +
            "\"tokenServiceUser\":\"" + TOKEN_SERVICE_USER + "\"," +
            "\"tokenServicePassword\":\"" + SECURED_VALUE + "\"," +
            "\"userIdSource\":\"" + USER_ID_SOURCE + "\"," +
            "\"nameIdFormat\":\"" + NAME_ID_FORMAT + "\"," +
            "\"authnContextClassRef\":\"" + AUTHN_CONTEXT_CLASS_REF + "\"" +
            "}";

    private static final SAMLBearerDestinationDto DESTINATION_DTO;
    private static final SAMLBearerDestinationDto DESTINATION_WITH_NULL_PASSWORDS_DTO;
    private static final SAMLBearerDestinationDto DESTINATION_WITH_INVALID_AUTH_TYPE;

    static {
        Map<String, Object> map = new HashMap<>(PROPERTIES);
        map.put(SAMLBearerDestinationDto.AUDIENCE_JSON_PROPERTY, AUDIENCE);
        map.put(SAMLBearerDestinationDto.CLIENT_KEY_JSON_PROPERTY, CLIENT_KEY);
        map.put(SAMLBearerDestinationDto.TOKEN_SERVICE_URL_JSON_PROPERTY, TOKEN_SERVICE_URL);
        map.put(SAMLBearerDestinationDto.TOKEN_SERVICE_USER_JSON_PROPERTY, TOKEN_SERVICE_USER);
        map.put(SAMLBearerDestinationDto.TOKEN_SERVICE_PASSWORD_JSON_PROPERTY, TOKEN_SERVICE_PASSWORD);
        map.put(DestinationDto.AUTHENTICATION_JSON_PROPERTY, AUTHENTICATION);
        map.put("userIdSource", USER_ID_SOURCE);
        map.put("nameIdFormat", NAME_ID_FORMAT);
        map.put("authnContextClassRef", AUTHN_CONTEXT_CLASS_REF);
        DESTINATION_DTO = new SAMLBearerDestinationDto(map);

        map = new HashMap<>(map);
        map.remove(SAMLBearerDestinationDto.TOKEN_SERVICE_PASSWORD_JSON_PROPERTY);
        map.remove(SAMLBearerDestinationDto.CLIENT_KEY_JSON_PROPERTY);
        DESTINATION_WITH_NULL_PASSWORDS_DTO = new SAMLBearerDestinationDto(map);

        map = new HashMap<>(map);
        map.remove(DestinationDto.AUTHENTICATION_JSON_PROPERTY);
        map.put(DestinationDto.AUTHENTICATION_JSON_PROPERTY, INVALID_AUTHENTICATION);
        DESTINATION_WITH_INVALID_AUTH_TYPE = new SAMLBearerDestinationDto(map);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void serializationTest() throws Exception {
        serializeSAMLBearerDestinationDtoTest();
        serializeSAMLBearerDestinationWithNullPasswordsDtoTest();
    }

    @Override
    public void deserializationTest() throws Exception {
        deserializeSAMLBearerDestinationDtoTest();
        deserializeSAMLBearerDestinationDtoWithNullPasswordsTest();
    }

    @Override
    public void equalsTest() {
        EqualsVerifier.forClass(SAMLBearerDestinationDto.class).verify();
    }

    @Override
    public void toStringTest() throws Exception {
        assertJsonEquals(TO_STRING, DESTINATION_DTO.toString());
    }

    @Test
    public void toEntityTest() {
        SAMLBearerDestination entity = DESTINATION_DTO.toEntity();

        assertEquals(NAME, entity.getName());
        assertEquals(URL, entity.getUrl());
        assertEquals(AUTHENTICATION, entity.getAuthenticationType());
        assertEquals(PROXY_TYPE, entity.getProxyType());
        assertEquals(DESTINATION_TYPE, entity.getDestinationType());
        assertEquals(TOKEN_SERVICE_URL, entity.getTokenServiceURL());
        assertEquals(TOKEN_SERVICE_USER, entity.getTokenServiceUser());
        assertArrayEquals(TOKEN_SERVICE_PASSWORD, entity.getTokenServicePassword());

        Map<String, String> additioanalProperties = entity.getAdditionalProperties();
        assertEquals(USER_ID_SOURCE, additioanalProperties.get("userIdSource"));
        assertEquals(NAME_ID_FORMAT, additioanalProperties.get("nameIdFormat"));
        assertEquals(AUTHN_CONTEXT_CLASS_REF, additioanalProperties.get("authnContextClassRef"));
    }

    @Test
    public void toEntityBadAuthTypeTest() {
        expectedException.expect(InvalidAuthenticationException.class);
        expectedException.expectMessage(MessageFormat.format(DESTINATION_AUTHENTICATION_TYPE_IS_NOT_VALID_MSG,
                AUTHENTICATION, INVALID_AUTHENTICATION));

        DESTINATION_WITH_INVALID_AUTH_TYPE.toEntity();
    }

    private void serializeSAMLBearerDestinationDtoTest() throws JsonProcessingException, JSONException {
        String json = objectMapper.writeValueAsString(DESTINATION_DTO);

        assertJsonEquals(JSON, json);
    }

    private void serializeSAMLBearerDestinationWithNullPasswordsDtoTest()
            throws JsonProcessingException, JSONException {
        String json = objectMapper.writeValueAsString(DESTINATION_WITH_NULL_PASSWORDS_DTO);

        assertJsonEquals(JSON_WITHOUT_PASSWORDS, json);
    }

    private void deserializeSAMLBearerDestinationDtoTest() throws IOException {
        SAMLBearerDestinationDto destinationDto = objectMapper.readValue(JSON,
                SAMLBearerDestinationDto.class);

        assertEquals(NAME, destinationDto.getName());
        assertEquals(URL, destinationDto.getUrl());
        assertEquals(AUTHENTICATION, destinationDto.getAuthentication());
        assertEquals(PROXY_TYPE, destinationDto.getProxyType());
        assertEquals(DESTINATION_TYPE, destinationDto.getDestinationType());
        assertEquals(AUDIENCE, destinationDto.getAudience());
        assertArrayEquals(CLIENT_KEY, destinationDto.getClientKey());
        assertEquals(TOKEN_SERVICE_URL, destinationDto.getTokenServiceURL());
        assertEquals(TOKEN_SERVICE_USER, destinationDto.getTokenServiceUser());
        assertArrayEquals(TOKEN_SERVICE_PASSWORD, destinationDto.getTokenServicePassword());

        Map<String, Object> additioanalProperties = destinationDto.getAdditionalProperties();
        assertEquals(USER_ID_SOURCE, additioanalProperties.get("userIdSource"));
        assertEquals(NAME_ID_FORMAT, additioanalProperties.get("nameIdFormat"));
        assertEquals(AUTHN_CONTEXT_CLASS_REF, additioanalProperties.get("authnContextClassRef"));
    }

    private void deserializeSAMLBearerDestinationDtoWithNullPasswordsTest() throws IOException {
        SAMLBearerDestinationDto destinationDto = objectMapper.readValue(JSON_WITHOUT_PASSWORDS,
                SAMLBearerDestinationDto.class);

        assertEquals(NAME, destinationDto.getName());
        assertEquals(URL, destinationDto.getUrl());
        assertEquals(AUTHENTICATION, destinationDto.getAuthentication());
        assertEquals(PROXY_TYPE, destinationDto.getProxyType());
        assertEquals(DESTINATION_TYPE, destinationDto.getDestinationType());
        assertEquals(AUDIENCE, destinationDto.getAudience());
        assertArrayEquals(new char[0], destinationDto.getClientKey());
        assertEquals(TOKEN_SERVICE_URL, destinationDto.getTokenServiceURL());
        assertEquals(TOKEN_SERVICE_USER, destinationDto.getTokenServiceUser());
        assertArrayEquals(new char[0], destinationDto.getTokenServicePassword());

        Map<String, Object> additioanalProperties = destinationDto.getAdditionalProperties();
        assertEquals(USER_ID_SOURCE, additioanalProperties.get("userIdSource"));
        assertEquals(NAME_ID_FORMAT, additioanalProperties.get("nameIdFormat"));
        assertEquals(AUTHN_CONTEXT_CLASS_REF, additioanalProperties.get("authnContextClassRef"));
    }

}
