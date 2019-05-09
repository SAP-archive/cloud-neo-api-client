package com.sap.cloud.rest.neo.api.client.service.configuration.dto;

import static com.sap.cloud.rest.neo.api.client.tests.utils.JsonUtil.assertJsonEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.rest.neo.api.client.AbstractDtoTest;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.AuthenticationType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.Destination;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.DestinationType;
import com.sap.cloud.rest.neo.api.client.service.configuration.model.ProxyType;

import nl.jqno.equalsverifier.EqualsVerifier;

public class DestinationDtoTest extends AbstractDtoTest {

    static final String NAME = "destination-name";
    static final String URL = "https://example.com";
    static final AuthenticationType AUTHENTICATION = AuthenticationType.NO_AUTH;
    static final ProxyType PROXY_TYPE = ProxyType.INTERNET;
    static final DestinationType DESTINATION_TYPE = DestinationType.HTTP;

    static final Map<String, Object> PROPERTIES;

    private static final String JSON = "{" +
            "\"Name\":\"" + NAME + "\"," +
            "\"URL\":\"" + URL + "\"," +
            "\"Authentication\":\"" + AUTHENTICATION + "\"," +
            "\"ProxyType\":\"" + PROXY_TYPE + "\"," +
            "\"Type\":\"" + DESTINATION_TYPE + "\"" +
            "}";

    private static final String TO_STRING = "{" +
            "\"Name\":\"" + NAME + "\"," +
            "\"URL\":\"" + URL + "\"," +
            "\"Authentication\":\"" + AUTHENTICATION + "\"," +
            "\"ProxyType\":\"" + PROXY_TYPE + "\"," +
            "\"Type\":\"" + DESTINATION_TYPE + "\"" +
            "}";
    private static final DestinationDto DESTINATION_DTO;
    private static final DestinationDto DESTINATION_DTO_STRING_ENUMS;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        PROPERTIES = new HashMap<>();
        PROPERTIES.put(DestinationDto.NAME_JSON_PROPERTY, NAME);
        PROPERTIES.put(DestinationDto.URL_JSON_PROPERTY, URL);
        PROPERTIES.put(DestinationDto.AUTHENTICATION_JSON_PROPERTY, AUTHENTICATION);
        PROPERTIES.put(DestinationDto.PROXY_TYPE_JSON_PROPERTY, PROXY_TYPE);
        PROPERTIES.put(DestinationDto.DESTINATION_TYPE_JSON_PROPERTY, DESTINATION_TYPE);

        DESTINATION_DTO = new DestinationDto(PROPERTIES);

        Map<String, Object> map = new HashMap<>();
        map.put(DestinationDto.NAME_JSON_PROPERTY, NAME);
        map.put(DestinationDto.URL_JSON_PROPERTY, URL);
        map.put(DestinationDto.AUTHENTICATION_JSON_PROPERTY, AUTHENTICATION.getName());
        map.put(DestinationDto.PROXY_TYPE_JSON_PROPERTY, PROXY_TYPE.getName());
        map.put(DestinationDto.DESTINATION_TYPE_JSON_PROPERTY, DESTINATION_TYPE.getName());

        DESTINATION_DTO_STRING_ENUMS = new DestinationDto(map);
    }

    @Override
    public void serializationTest() throws Exception {
        String json = objectMapper.writeValueAsString(DESTINATION_DTO);

        assertJsonEquals(JSON, json);

        json = objectMapper.writeValueAsString(DESTINATION_DTO_STRING_ENUMS);

        assertJsonEquals(JSON, json);
    }

    @Override
    public void deserializationTest() throws Exception {
        DestinationDto destinationDto = objectMapper.readValue(JSON, DestinationDto.class);

        assertEquals(NAME, destinationDto.getName());
        assertEquals(URL, destinationDto.getUrl());
        assertEquals(AUTHENTICATION, destinationDto.getAuthentication());
        assertEquals(PROXY_TYPE, destinationDto.getProxyType());
        assertEquals(DESTINATION_TYPE, destinationDto.getDestinationType());
    }

    @Override
    public void equalsTest() {
        EqualsVerifier.forClass(DestinationDto.class).verify();

        HashMap<String, Object> basicMap = new HashMap<>(DESTINATION_DTO.getProperties());
        basicMap.put("pass", "test-pass".toCharArray());
        basicMap.put("user", null);
        basicMap.put("user2", null);
        DestinationDto destinationConfigurationDto = new DestinationDto(basicMap);
        DestinationDto destinationConfigurationDto2 = new DestinationDto(
                new HashMap<>(basicMap));
        assertEquals(destinationConfigurationDto, destinationConfigurationDto2);

        HashMap<String, Object> modifiedMap = new HashMap<>(basicMap);
        modifiedMap.remove("user2");
        destinationConfigurationDto2 = new DestinationDto(modifiedMap);
        assertNotEquals(destinationConfigurationDto, destinationConfigurationDto2);

        modifiedMap = new HashMap<>(basicMap);
        modifiedMap.put("user", "abc");
        modifiedMap.put("user2", "cda");
        destinationConfigurationDto2 = new DestinationDto(modifiedMap);
        assertNotEquals(destinationConfigurationDto, destinationConfigurationDto2);

        modifiedMap = new HashMap<>(basicMap);
        modifiedMap.put("pass", "test-pass-2".toCharArray());
        destinationConfigurationDto2 = new DestinationDto(modifiedMap);
        assertNotEquals(destinationConfigurationDto, destinationConfigurationDto2);

        modifiedMap = new HashMap<>(basicMap);
        modifiedMap.put("pass", "test-pass-2");
        destinationConfigurationDto2 = new DestinationDto(modifiedMap);
        assertNotEquals(destinationConfigurationDto, destinationConfigurationDto2);

    }

    @Override
    public void toStringTest() throws JSONException {
        assertJsonEquals(TO_STRING, DESTINATION_DTO.toString());
    }

    @Test
    public void toEntityTest() {
        Destination entity = DESTINATION_DTO.toEntity();

        assertEquals(NAME, entity.getName());
        assertEquals(URL, entity.getUrl());
        assertEquals(AUTHENTICATION, entity.getAuthenticationType());
        assertEquals(PROXY_TYPE, entity.getProxyType());
        assertEquals(DESTINATION_TYPE, entity.getDestinationType());

        entity = DESTINATION_DTO_STRING_ENUMS.toEntity();

        assertEquals(NAME, entity.getName());
        assertEquals(URL, entity.getUrl());
        assertEquals(AUTHENTICATION, entity.getAuthenticationType());
        assertEquals(PROXY_TYPE, entity.getProxyType());
        assertEquals(DESTINATION_TYPE, entity.getDestinationType());
    }
}
