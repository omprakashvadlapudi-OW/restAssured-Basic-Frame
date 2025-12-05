package utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class FileUtils {

	public static String readJson(String filePath) {
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (Exception e) {
			throw new RuntimeException("Unable to read file: " + filePath, e);
		}
	}

	
	public static String getValue(String jsonString, String key) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(jsonString);

			JsonNode valueNode = node.get(key);
			return valueNode != null ? valueNode.asText() : null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String applyDataToTemplate(String template, Map<String, String> data) {
	    String finalJson = template;

	    for (Map.Entry<String, String> entry : data.entrySet()) {
	        String key = entry.getKey();
	        String rawValue = entry.getValue();
	        String processedValue = DynamicValueProcessor.process(rawValue);

	        String placeholder = "\\$\\{" + Pattern.quote(key) + "\\}";

	        finalJson = finalJson.replaceAll(placeholder, Matcher.quoteReplacement(processedValue));
	    }

	    return finalJson;
	}



}
