package utils;

import java.util.Map;

public class TestDataMerger {

	public static Map<String, String> merge(Map<String, String> base, Map<String, String> override) {

		if (base == null)
			return override;
		if (override == null)
			return base;

		for (Map.Entry<String, String> entry : override.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (value == null || value.trim().isEmpty()) {
				continue;
			}

			base.put(key, value);
		}

		return base;
	}

}
