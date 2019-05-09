package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import static com.sap.cloud.rest.neo.api.client.service.configuration.model.DestinationType.valueOfName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class DestinationTypeTest {

    private static final String HTTP_TYPE_NAME = "HTTP";
    private static final String RFC_TYPE_NAME = "RFC";
    private static final String MAIL_TYPE_NAME = "MAIL";
    private static final String LDAP_TYPE_NAME = "LDAP";
    private static final String INVALID_TYPE_NAME = "invalid";

    @Test
    public void valueOfNameTest() {
        assertEquals(DestinationType.HTTP, valueOfName(HTTP_TYPE_NAME));
        assertEquals(DestinationType.RFC, valueOfName(RFC_TYPE_NAME));
        assertEquals(DestinationType.MAIL, valueOfName(MAIL_TYPE_NAME));
        assertEquals(DestinationType.LDAP, valueOfName(LDAP_TYPE_NAME));

    }

    @Test
    public void valueOfNameTest_returnsNull() {
        assertNull(valueOfName(INVALID_TYPE_NAME));
    }

    @Test
    public void getNameTest() {
        assertEquals(HTTP_TYPE_NAME, DestinationType.HTTP.getName());
        assertEquals(RFC_TYPE_NAME, DestinationType.RFC.getName());
        assertEquals(MAIL_TYPE_NAME, DestinationType.MAIL.getName());
        assertEquals(LDAP_TYPE_NAME, DestinationType.LDAP.getName());
    }
}