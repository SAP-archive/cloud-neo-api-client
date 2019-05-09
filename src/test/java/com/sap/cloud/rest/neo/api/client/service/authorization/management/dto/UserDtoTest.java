package com.sap.cloud.rest.neo.api.client.service.authorization.management.dto;

import static com.sap.cloud.rest.neo.api.client.tests.utils.JsonUtil.assertJsonEquals;
import static org.junit.Assert.assertEquals;

import org.json.JSONException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.rest.neo.api.client.AbstractDtoTest;

import nl.jqno.equalsverifier.EqualsVerifier;

public class UserDtoTest extends AbstractDtoTest {

    private static final String NAME = "my-user-name";

    private static final UserDto USER = new UserDto(NAME);

    private static final String JSON = "{"
            + "\"name\":\"" + NAME + "\""
            + "}";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void serializationTest() throws Exception {
        String json = objectMapper.writeValueAsString(USER);
        assertJsonEquals(JSON, json);
    }

    @Override
    public void deserializationTest() throws Exception {
        UserDto role = objectMapper.readValue(JSON, UserDto.class);
        assertEquals(NAME, role.getName());
    }

    @Override
    public void equalsTest() {
        EqualsVerifier.forClass(UserDto.class).usingGetClass().verify();
    }

    @Override
    public void toStringTest() throws JSONException {
        assertJsonEquals(JSON, USER.toString());
    }

}
