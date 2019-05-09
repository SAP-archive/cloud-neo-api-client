package com.sap.cloud.rest.neo.api.client.service.configuration.model;

import static com.sap.cloud.rest.neo.api.client.service.configuration.model.AuthenticationType.valueOfName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class AuthenticationTypeTest {

    private static final String CLIENT_CERT_NAME = "ClientCertificateAuthentication";
    private static final String BASIC_NAME = "BasicAuthentication";
    private static final String SAML_BEARER_NAME = "OAuth2SAMLBearerAssertion";
    private static final String NO_AUTH = "NoAuthentication";
    private static final String INVALID_NAME = "invalid";

    @Test
    public void valueOfDisplayNameTest() {
        assertEquals(AuthenticationType.CLIENT_CERT, valueOfName(CLIENT_CERT_NAME));
        assertEquals(AuthenticationType.BASIC, valueOfName(BASIC_NAME));
        assertEquals(AuthenticationType.SAML_BEARER, valueOfName(SAML_BEARER_NAME));
        assertEquals(AuthenticationType.NO_AUTH, valueOfName(NO_AUTH));
    }

    @Test
    public void valueOfDisplayNameTest_returnsNull() {
        assertNull(valueOfName(INVALID_NAME));
    }

    @Test
    public void getNameTest() {
        assertEquals(CLIENT_CERT_NAME, AuthenticationType.CLIENT_CERT.getName());
        assertEquals(BASIC_NAME, AuthenticationType.BASIC.getName());
        assertEquals(SAML_BEARER_NAME, AuthenticationType.SAML_BEARER.getName());
        assertEquals(NO_AUTH, AuthenticationType.NO_AUTH.getName());
    }

}
