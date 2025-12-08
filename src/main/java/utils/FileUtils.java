package utils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class FileUtils {

	private static final ObjectMapper MAPPER = new ObjectMapper();


	public static String readJson(String filePath) {
		try {
			Path path = Paths.get(filePath);
			return Files.readString(path, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException("Unable to read file: " + filePath, e);
		}
	}


	public static String getValue(String jsonString, String key) {
		try {
			JsonNode node = MAPPER.readTree(jsonString);
			JsonNode valueNode = node.get(key);
			return valueNode != null ? valueNode.asText() : null;
		} catch (Exception e) {
			System.err.println("Error extracting value for key '" + key + "': " + e.getMessage());
			return null;
		}
	}

	public static String applyDataToTemplate(String template, Map<String, String> data) {
		if (template == null || data == null) {
			return template;
		}

		String result = template;

		for (Map.Entry<String, String> entry : data.entrySet()) {
			String key = entry.getKey();
			String rawValue = entry.getValue();

			// Handle REMOVE - remove entire line
			if ("REMOVE".equalsIgnoreCase(rawValue)) {
				String placeholder = "\\$\\{" + Pattern.quote(key) + "\\}";
				result = result.replaceAll(".*" + placeholder + ".*\\n?", "");
				continue;
			}

			// Handle NULL - replace with JSON null (not string)
			if ("NULL".equalsIgnoreCase(rawValue)) {
				// Remove quotes around placeholder: "${key}" -> null
				String quotedPlaceholder = "\"\\$\\{" + Pattern.quote(key) + "\\}\"";
				result = result.replaceAll(quotedPlaceholder, "null");
				continue;
			}

			// Handle BLANK or other values
			String processedValue;
			if ("BLANK".equalsIgnoreCase(rawValue)) {
				processedValue = "";
			} else {
				// Process dynamic values (RANDOM(), RANDOM_EMAIL(), etc.)
				processedValue = DynamicValueProcessor.process(rawValue);
			}

			// Replace ${key} with value
			String placeholder = "\\$\\{" + Pattern.quote(key) + "\\}";
			result = result.replaceAll(placeholder, Matcher.quoteReplacement(processedValue));
		}

		return result;
	}
}
