package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties configData = new Properties();

    public static Properties fetchProperties() {
        if (!configData.isEmpty()) {
            return configData; // already loaded
        }
        // Load from classpath so it works in IDE, Maven, or packaged runs
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                Report.error("Fatal Error: config.properties not found on classpath", null);
                throw new RuntimeException("Could not find config.properties on classpath");
            }
            configData.load(in);
        } catch (IOException e) {
            Report.error("Fatal Error: Failed to parse config.properties!", e);
            throw new RuntimeException("Could not load config.properties", e);
        }
        return configData;
    }
}
