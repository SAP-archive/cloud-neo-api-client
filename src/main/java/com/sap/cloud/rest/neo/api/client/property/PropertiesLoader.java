package com.sap.cloud.rest.neo.api.client.property;

import static org.slf4j.helpers.MessageFormatter.format;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    static final String LOADING_FILE_FAILED_MSG = "Loading [{}] file failed.";

    private Properties properties;

    public PropertiesLoader(String filePath) {
        this.properties = loadProperties(filePath);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    private Properties loadProperties(String filePath) {
        Properties properties = new Properties();

        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filePath)) {
            properties.load(stream);
        } catch (Exception e) {
            throw new PropertiesLoaderException(format(LOADING_FILE_FAILED_MSG, filePath).getMessage(), e);
        }

        return properties;
    }
}