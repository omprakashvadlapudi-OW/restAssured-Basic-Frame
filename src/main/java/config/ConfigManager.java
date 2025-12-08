package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static Properties props = new Properties();

	static {
		loadProperties();
	}

	
	private static void loadProperties() {
		String configFile = "properties/base.properties";

		try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream(configFile)) {
			if (is != null) {
				props.load(is);
				System.out.println("Loaded configuration from classpath: " + configFile);
			} else {
				try (FileInputStream fis = new FileInputStream("src/test/resources/" + configFile)) {
					props.load(fis);
					System.out.println("Loaded configuration from file system: " + configFile);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to load configuration: " + configFile, e);
		}
	}

	
	public static String get(String key) {
		return props.getProperty(key);
	}


	public static String get(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}
}
