package com.sap.cloud.rest.neo.api.client.service.authorization.management.model;

import static com.sap.cloud.rest.neo.api.client.tests.utils.JsonUtil.assertJsonEquals;
import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class UserTest {

    private static final String NAME = "my-username";

    private static final User USER = new User(NAME);

    private static final String TO_STRING = "{"
            + "\"name\":\"" + NAME + "\""
            + "}";

    @Test
    public void getName() {
        assertEquals(NAME, USER.getName());
    }

    @Test
    public void equalsTest() {
        EqualsVerifier.forClass(User.class).usingGetClass().verify();
    }

    @Test
    public void toStringTest() throws JSONException {
        assertJsonEquals(TO_STRING, USER.toString());
    }
}
