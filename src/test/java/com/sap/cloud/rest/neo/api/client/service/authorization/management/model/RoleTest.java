package com.sap.cloud.rest.neo.api.client.service.authorization.management.model;

import static com.sap.cloud.rest.neo.api.client.tests.utils.JsonUtil.assertJsonEquals;
import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class RoleTest {

    private static final String NAME = "my-role-name";
    private static final String TYPE = "custom";
    private static final boolean SHARED = false;
    private static final boolean APPLICATON_ROLE = true;

    private static final Role ROLE = new Role(NAME, TYPE, APPLICATON_ROLE, SHARED);

    private static final String TO_STRING = "{"
            + "\"name\":\"" + NAME + "\","
            + "\"shared\":" + SHARED + ","
            + "\"applicatonRole\":" + APPLICATON_ROLE + ","
            + "\"type\":\"" + TYPE + "\""
            + "}";

    @Test
    public void getMethodsTest() {
        assertEquals(NAME, ROLE.getName());
        assertEquals(TYPE, ROLE.getType());
        assertEquals(APPLICATON_ROLE, ROLE.isApplicationRole());
        assertEquals(SHARED, ROLE.isShared());
    }

    @Test
    public void equalsTest() {
        EqualsVerifier.forClass(Role.class).usingGetClass().verify();
    }

    @Test
    public void toStringTest() throws JSONException {
        assertJsonEquals(TO_STRING, ROLE.toString());
    }
}
