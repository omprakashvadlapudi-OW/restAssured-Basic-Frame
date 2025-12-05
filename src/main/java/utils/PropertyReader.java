package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
	private static Properties props = new Properties();

	static {
		try {
			FileInputStream fis = new FileInputStream("src/test/resources/properties/base.properties");
			props.load(fis);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load configuration", e);
		}
	}

	public static String get(String key) {
		return props.getProperty(key);
	}
}
