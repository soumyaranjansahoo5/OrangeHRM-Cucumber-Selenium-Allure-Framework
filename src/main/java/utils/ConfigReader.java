package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads key/value pairs from src/test/resources/config.properties so that
 * environment specific values (browser, URL, credentials) never need to be
 * hardcoded inside Page Objects or Step Definitions.
 */
public class ConfigReader {

    private static final String CONFIG_PATH = "src/test/resources/config.properties";
    private static Properties properties;

    private ConfigReader() {
        // utility class - no instances
    }

    private static void loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException("Unable to load config.properties from path: " + CONFIG_PATH, e);
            }
        }
    }

    public static String get(String key) {
        loadProperties();
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    public static String getBrowser() {
        return get("browser");
    }

    public static String getUrl() {
        return get("url");
    }

    public static String getUsername() {
        return get("username");
    }

    public static String getPassword() {
        return get("password");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(get("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(get("explicit.wait"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless"));
    }
}
