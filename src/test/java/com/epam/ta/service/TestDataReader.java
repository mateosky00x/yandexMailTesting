package com.epam.ta.service;

import java.util.ResourceBundle;

public class TestDataReader {
    private static final String DEFAULT_ENV = "testdata"; // fallback to testdata.properties
    private static ResourceBundle resourceBundle;

    static {
        String env = System.getProperty("environment");
        if (env == null || env.isEmpty()) {
            System.out.println("⚠️ Warning: 'environment' system property not set. Falling back to default: " + DEFAULT_ENV);
            env = DEFAULT_ENV;
        }
        try {
            resourceBundle = ResourceBundle.getBundle(env);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load resource bundle for environment: " + env, e);
        }
    }

    public static String getTestData(String key){
        return resourceBundle.getString(key);
    }
}
