package com.sap.cloud.rest.neo.api.client.property;

import static com.sap.cloud.rest.neo.api.client.property.PropertiesLoader.LOADING_FILE_FAILED_MSG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.slf4j.helpers.MessageFormatter.format;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PropertiesLoaderTest {

    private static final String NON_EXISTING_FILE_PATH = "non-existing-file.properties";
    private static final String PROPERTIES_FILE_PATH = "test.properties";
    private static final String PROPERTY_KEY = "property_key";
    private static final String PROPERTY_VALUE = "property_value";

    private static PropertiesLoader propertiesLoader;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void initPropertiesLoader_throwsException() {
        expectedException.expect(PropertiesLoaderException.class);
        expectedException.expectMessage(format(LOADING_FILE_FAILED_MSG, NON_EXISTING_FILE_PATH).getMessage());

        propertiesLoader = new PropertiesLoader(NON_EXISTING_FILE_PATH);
    }

    @Test
    public void getPropertyTest() {
        propertiesLoader = new PropertiesLoader(PROPERTIES_FILE_PATH);
        assertEquals(PROPERTY_VALUE, propertiesLoader.getProperty(PROPERTY_KEY));
    }

    @Test
    public void getPropertyTest_propertyNotFound() {
        propertiesLoader = new PropertiesLoader(PROPERTIES_FILE_PATH);
        assertNull(propertiesLoader.getProperty("non-existing-property-key"));
    }
}