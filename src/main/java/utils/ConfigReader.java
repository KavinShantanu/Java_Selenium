package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader
{
    private static final Properties configData = new Properties();

    public static Properties fetchProperties() {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            configData.load(file); // load the file
        } catch (IOException e) {
            LogRecorder.error("Fatal Error: Failed to locate or parse config.properties!",e);
            throw new RuntimeException("Could not find or load config.properties file!");
        }
        return configData;
    }

}
