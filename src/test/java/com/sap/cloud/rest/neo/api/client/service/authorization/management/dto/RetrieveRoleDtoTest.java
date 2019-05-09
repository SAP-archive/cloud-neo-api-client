package com.sap.cloud.rest.neo.api.client.service.authorization.management.dto;

import static com.sap.cloud.rest.neo.api.client.tests.utils.JsonUtil.assertJsonEquals;
import static org.junit.Assert.assertEquals;

import org.json.JSONException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.rest.neo.api.client.AbstractDtoTest;

import nl.jqno.equalsverifier.EqualsVerifier;

public class RetrieveRoleDtoTest extends AbstractDtoTest {

    private static final String NAME = "my-role-name";
    private static final String TYPE = "custom";
    private static final boolean SHARED = false;
    private static final boolean APPLICATON_ROLE = true;

    private static final RoleDto ROLE = new RetrieveRoleDto(NAME, TYPE, APPLICATON_ROLE, SHARED);

    private static final String JSON = "{"
            + "\"name\":\"" + NAME + "\","
            + "\"shared\":" + SHARED + ","
            + "\"applicatonRole\":" + APPLICATON_ROLE + ","
            + "\"type\":\"" + TYPE + "\""
            + "}";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void serializationTest() throws Exception {
        String json = objectMapper.writeValueAsString(ROLE);
        assertJsonEquals(JSON, json);
    }

    @Override
    public void deserializationTest() throws Exception {
        RetrieveRoleDto role = objectMapper.readValue(JSON, RetrieveRoleDto.class);
        assertEquals(NAME, role.getName());
        assertEquals(TYPE, role.getType());
        assertEquals(APPLICATON_ROLE, role.isApplicationRole());
        assertEquals(SHARED, role.isShared());
    }

    @Override
    public void equalsTest() {
        EqualsVerifier.forClass(RetrieveRoleDto.class).usingGetClass().verify();
    }

    @Override
    public void toStringTest() throws JSONException {
        assertJsonEquals(JSON, ROLE.toString());
    }

}