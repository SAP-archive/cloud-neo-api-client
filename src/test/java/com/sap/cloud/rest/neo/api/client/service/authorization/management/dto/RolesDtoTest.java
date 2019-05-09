package com.sap.cloud.rest.neo.api.client.service.authorization.management.dto;

import static com.sap.cloud.rest.neo.api.client.tests.utils.JsonUtil.assertJsonEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.cloud.rest.neo.api.client.AbstractDtoTest;

import nl.jqno.equalsverifier.EqualsVerifier;

public class RolesDtoTest extends AbstractDtoTest {

    private static final String TEST_ROLE_1 = "test-role-1";
    private static final String TEST_ROLE_2 = "test-role-2";

    private List<RoleDto> createRolesList = Arrays.asList(new CreateRoleDto(TEST_ROLE_1),
            new CreateRoleDto(TEST_ROLE_2));

    private RolesDto<RoleDto> createRolesDto = new RolesDto<>(createRolesList);

    private static final String JSON = "{" +
            "  \"roles\": [" +
            "    {" +
            "      \"name\" : \"" + TEST_ROLE_1 + "\"" +
            "    }, " +
            "    {" +
            "      \"name\" : \"" + TEST_ROLE_2 + "\"" +
            "    }" +
            "  ]" +
            "}";

    @Override
    public void serializationTest() throws Exception {
        String json = objectMapper.writeValueAsString(createRolesDto);
        assertJsonEquals(JSON, json);

    }

    @Override
    public void deserializationTest() throws Exception {
        TypeReference<RolesDto<CreateRoleDto>> rolesDtoType = new TypeReference<RolesDto<CreateRoleDto>>() {
        };
        RolesDto<CreateRoleDto> createRolesDto = objectMapper.readValue(JSON, rolesDtoType);
        List<CreateRoleDto> list = createRolesDto.getRolesList();
        assertEquals(2, list.size());
        assertEquals(TEST_ROLE_1, list.get(0).getName());
        assertEquals(TEST_ROLE_2, list.get(1).getName());

    }

    @Override
    public void equalsTest() {
        EqualsVerifier.forClass(RolesDto.class).usingGetClass().verify();
    }

    @Override
    public void toStringTest() throws JSONException {
        assertJsonEquals(JSON, createRolesDto.toString());
    }

}
