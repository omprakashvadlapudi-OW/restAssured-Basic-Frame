package utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseProcess {
	public static Map<String, String> parseResponseExpectations(String responseBlock) {
		Map<String, String> map = new HashMap<>();

		if (responseBlock == null || responseBlock.trim().isEmpty()) {
			return map;
		}

		responseBlock = responseBlock.replace("\\n", "\n");
		responseBlock = responseBlock.replace("\r", "\n");

		String[] pairs = responseBlock.split("[\\n ]+");

		for (String p : pairs) {
			if (p.contains("=")) {
				String[] kv = p.split("=", 2);
				map.put(kv[0].trim(), kv[1].trim());
			}
		}
		return map;
	}

}
