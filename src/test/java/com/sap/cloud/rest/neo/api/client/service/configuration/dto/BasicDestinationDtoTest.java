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
import com.sap.cloud.rest.neo.api.client.service.configuration.model.BasicDestination;

import nl.jqno.equalsverifier.EqualsVerifier;

public class BasicDestinationDtoTest extends AbstractDtoTest {

    private static final String USER = "my-user";
    private static final char[] PASSWORD = "my-password".toCharArray();
    private static final String USER_ID_SOURCE = "user-id-source";
    private static final String NAME_ID_FORMAT = "name-id-format";
    private static final String AUTHN_CONTEXT_CLASS_REF = "authn-context-class-ref";
    private static final AuthenticationType AUTHENTICATION = AuthenticationType.BASIC;
    private static final AuthenticationType INVALID_AUTHENTICATION = AuthenticationType.CLIENT_CERT;

    private static final String JSON = "{" +
            "\"Name\":\"" + NAME + "\"," +
            "\"URL\":\"" + URL + "\"," +
            "\"Authentication\":\"" + AUTHENTICATION + "\"," +
            "\"ProxyType\":\"" + PROXY_TYPE + "\"," +
            "\"Type\":\"" + DESTINATION_TYPE + "\"," +
            "\"User\":\"" + USER + "\"," +
            "\"Password\":\"" + String.valueOf(PASSWORD) + "\"," +
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
            "\"User\":\"" + USER + "\"," +
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
            "\"User\":\"" + USER + "\"," +
            "\"Password\":\"" + SECURED_VALUE + "\"," +
            "\"userIdSource\":\"" + USER_ID_SOURCE + "\"," +
            "\"nameIdFormat\":\"" + NAME_ID_FORMAT + "\"," +
            "\"authnContextClassRef\":\"" + AUTHN_CONTEXT_CLASS_REF + "\"" +
            "}";

    private static final BasicDestinationDto DESTINATION_DTO;
    private static final BasicDestinationDto DESTINATION_WITH_NULL_PASSWORDS_DTO;
    private static final BasicDestinationDto DESTINATION_WITH_INVALID_AUTH_TYPE;

    static {
        HashMap<String, Object> map = new HashMap<>(PROPERTIES);
        map.put(BasicDestinationDto.USER_JSON_PROPERTY, USER);
        map.put(BasicDestinationDto.PASSWORD_JSON_PROPERTY, PASSWORD);
        map.put(DestinationDto.AUTHENTICATION_JSON_PROPERTY, AUTHENTICATION);
        map.put("userIdSource", USER_ID_SOURCE);
        map.put("nameIdFormat", NAME_ID_FORMAT);
        map.put("authnContextClassRef", AUTHN_CONTEXT_CLASS_REF);
        DESTINATION_DTO = new BasicDestinationDto(map);

        map = new HashMap<>(map);
        map.remove(BasicDestinationDto.PASSWORD_JSON_PROPERTY);
        DESTINATION_WITH_NULL_PASSWORDS_DTO = new BasicDestinationDto(map);

        map = new HashMap<>(map);
        map.remove(DestinationDto.AUTHENTICATION_JSON_PROPERTY);
        map.put(DestinationDto.AUTHENTICATION_JSON_PROPERTY, INVALID_AUTHENTICATION);
        DESTINATION_WITH_INVALID_AUTH_TYPE = new BasicDestinationDto(map);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void serializationTest() throws Exception {
        serializeBasicDestinationDtoTest();
        serializeBasicDestinationWithNullPasswordsDtoTest();
    }

    @Override
    public void deserializationTest() throws Exception {
        deserializeBasicDestinationDtoTest();
        deserializeBasicDestinationDtoWithNullPasswordsTest();
    }

    @Override
    public void equalsTest() {
        EqualsVerifier.forClass(BasicDestinationDto.class).verify();
    }

    @Override
    public void toStringTest() throws JSONException {
        assertJsonEquals(TO_STRING, DESTINATION_DTO.toString());
    }

    @Test
    public void toEntityTest() {
        BasicDestination entity = DESTINATION_DTO.toEntity();

        assertEquals(NAME, entity.getName());
        assertEquals(URL, entity.getUrl());
        assertEquals(AUTHENTICATION, entity.getAuthenticationType());
        assertEquals(PROXY_TYPE, entity.getProxyType());
        assertEquals(DESTINATION_TYPE, entity.getDestinationType());
        assertEquals(USER, entity.getUser());
        assertArrayEquals(PASSWORD, entity.getPassword());

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

    private void serializeBasicDestinationDtoTest() throws JsonProcessingException, JSONException {
        String json = objectMapper.writeValueAsString(DESTINATION_DTO);
        assertJsonEquals(JSON, json);

    }

    private void serializeBasicDestinationWithNullPasswordsDtoTest()
            throws JsonProcessingException, JSONException {
        String json = objectMapper.writeValueAsString(DESTINATION_WITH_NULL_PASSWORDS_DTO);

        assertJsonEquals(JSON_WITHOUT_PASSWORDS, json);

    }

    private void deserializeBasicDestinationDtoTest() throws IOException {
        BasicDestinationDto destinationDto = objectMapper.readValue(JSON,
                BasicDestinationDto.class);

        assertEquals(NAME, destinationDto.getName());
        assertEquals(URL, destinationDto.getUrl());
        assertEquals(AUTHENTICATION, destinationDto.getAuthentication());
        assertEquals(PROXY_TYPE, destinationDto.getProxyType());
        assertEquals(DESTINATION_TYPE, destinationDto.getDestinationType());
        assertEquals(USER, destinationDto.getUser());
        assertArrayEquals(PASSWORD, destinationDto.getPassword());

        Map<String, Object> additioanalProperties = destinationDto.getAdditionalProperties();
        assertEquals(USER_ID_SOURCE, additioanalProperties.get("userIdSource"));
        assertEquals(NAME_ID_FORMAT, additioanalProperties.get("nameIdFormat"));
        assertEquals(AUTHN_CONTEXT_CLASS_REF, additioanalProperties.get("authnContextClassRef"));
    }

    private void deserializeBasicDestinationDtoWithNullPasswordsTest() throws IOException {
        BasicDestinationDto destinationDto = objectMapper.readValue(JSON_WITHOUT_PASSWORDS,
                BasicDestinationDto.class);

        assertEquals(NAME, destinationDto.getName());
        assertEquals(URL, destinationDto.getUrl());
        assertEquals(AUTHENTICATION, destinationDto.getAuthentication());
        assertEquals(PROXY_TYPE, destinationDto.getProxyType());
        assertEquals(DESTINATION_TYPE, destinationDto.getDestinationType());
        assertEquals(USER, destinationDto.getUser());
        assertArrayEquals(new char[0], destinationDto.getPassword());

        Map<String, Object> additioanalProperties = destinationDto.getAdditionalProperties();
        assertEquals(USER_ID_SOURCE, additioanalProperties.get("userIdSource"));
        assertEquals(NAME_ID_FORMAT, additioanalProperties.get("nameIdFormat"));
        assertEquals(AUTHN_CONTEXT_CLASS_REF, additioanalProperties.get("authnContextClassRef"));
    }
}