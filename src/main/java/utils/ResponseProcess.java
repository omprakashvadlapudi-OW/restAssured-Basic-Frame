package utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseProcess {

	public static Map<String, String> parseResponseExpectations(String responseString) {
		Map<String, String> expectations = new HashMap<>();

		if (responseString == null || responseString.trim().isEmpty()) {
			return expectations;
		}

		// Replace newlines with commas for consistent parsing
		String normalized = responseString.replace("\n", ",").replace("\r", "");

		String[] pairs = normalized.split(",");
		for (String pair : pairs) {
			String[] keyValue = pair.split("=", 2);
			if (keyValue.length == 2) {
				expectations.put(keyValue[0].trim(), keyValue[1].trim());
			}
		}

		return expectations;
	}
}
