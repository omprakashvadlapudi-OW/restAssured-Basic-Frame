package utils;

import java.util.HashMap;
import java.util.Map;


public class TestDataMerger {

	public static Map<String, String> merge(Map<String, String> baseData, Map<String, String> testData) {
		Map<String, String> merged = new HashMap<>(baseData);
		merged.putAll(testData);
		return merged;
	}
}
