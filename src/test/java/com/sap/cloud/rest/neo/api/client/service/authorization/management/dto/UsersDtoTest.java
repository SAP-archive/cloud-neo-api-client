package com.sap.cloud.rest.neo.api.client.service.authorization.management.dto;

import static com.sap.cloud.rest.neo.api.client.tests.utils.JsonUtil.assertJsonEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;

import com.sap.cloud.rest.neo.api.client.AbstractDtoTest;

import nl.jqno.equalsverifier.EqualsVerifier;

public class UsersDtoTest extends AbstractDtoTest {

    private static final String TEST_USER_1 = "test-user-1";
    private static final String TEST_USER_2 = "test-user-2";

    private List<UserDto> usersList = Arrays.asList(new UserDto(TEST_USER_1), new UserDto(TEST_USER_2));

    private UsersDto usersDto = new UsersDto(usersList);

    private static final String JSON = "{" +
            "  \"users\": [" +
            "    {" +
            "      \"name\" : \"" + TEST_USER_1 + "\"" +
            "    }," +
            "    {" +
            "      \"name\" : \"" + TEST_USER_2 + "\"" +
            "    }" +
            "  ]" +
            "}";

    @Override
    public void serializationTest() throws Exception {
        String json = objectMapper.writeValueAsString(usersDto);
        assertJsonEquals(JSON, json);
    }

    @Override
    public void deserializationTest() throws Exception {
        UsersDto usersDto = objectMapper.readValue(JSON, UsersDto.class);
        List<UserDto> list = usersDto.getUsersList();
        assertEquals(2, list.size());
        assertEquals(TEST_USER_1, list.get(0).getName());
        assertEquals(TEST_USER_2, list.get(1).getName());
    }

    @Override
    public void equalsTest() {
        EqualsVerifier.forClass(UsersDto.class).usingGetClass().verify();
    }

    @Override
    public void toStringTest() throws JSONException {
        assertJsonEquals(JSON, usersDto.toString());
    }

}
