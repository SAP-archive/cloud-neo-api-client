package com.sap.cloud.rest.neo.api.client.tests.utils;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonUtil {

    public static void assertJsonEquals(String expected, String actual) throws JSONException {
        JSONAssert.assertEquals(expected, actual, true);
    }
}
