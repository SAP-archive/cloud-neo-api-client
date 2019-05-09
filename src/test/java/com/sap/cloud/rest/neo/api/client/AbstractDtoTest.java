package com.sap.cloud.rest.neo.api.client;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractDtoTest {

    protected ObjectMapper objectMapper = new ObjectMapper();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public abstract void serializationTest() throws Exception;

    @Test
    public abstract void deserializationTest() throws Exception;

    @Test
    public abstract void equalsTest() throws Exception;

    @Test
    public abstract void toStringTest() throws Exception;
}
