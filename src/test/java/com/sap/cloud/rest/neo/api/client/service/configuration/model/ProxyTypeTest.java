package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import static com.sap.cloud.rest.neo.api.client.service.configuration.model.ProxyType.valueOfName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ProxyTypeTest {

    private static final String INTERNET_TYPE_NAME = "Internet";
    private static final String ON_PREMISE_TYPE_NAME = "OnPremise";
    private static final String INVALID_TYPE_NAME = "invalid";

    @Test
    public void valueOfNameTest() {
        assertEquals(ProxyType.INTERNET, valueOfName(INTERNET_TYPE_NAME));
        assertEquals(ProxyType.ON_PREMISE, valueOfName(ON_PREMISE_TYPE_NAME));

    }

    @Test
    public void valueOfNameTest_returnsNull() {
        assertNull(valueOfName(INVALID_TYPE_NAME));
    }

    @Test
    public void getNameTest() {
        assertEquals(INTERNET_TYPE_NAME, ProxyType.INTERNET.getName());
        assertEquals(ON_PREMISE_TYPE_NAME, ProxyType.ON_PREMISE.getName());
    }
}