package utils;

import java.util.HashMap;
import java.util.Map;

import config.ConfigManager;

public class RequestProcess {

	public static String requestBuild(String templatePath, String initialCSV, Map<String, String> testDataRow) {

	    // 1. Parse test_data block
	    Map<String, String> parsedTestData = parseRequestBlock(testDataRow);

	    // 2. Read base CSV
	    Map<String, String> baseData = CsvUtils.readCsvAsMap(initialCSV);

	    // 3. Merge
	    Map<String, String> mergedData = TestDataMerger.merge(baseData, parsedTestData);

	    // 4. Determine template
	    String requestContent = mergedData.get("request");
	    String finalTemplate;

	    if (requestContent != null && !requestContent.trim().isEmpty()) {
	        // 👉 request content IS the template
	        finalTemplate = FileUtils.readJson(ConfigManager.get("request.folder") + requestContent);;
	    } else {
	        // 👉 load JSON template from path
	        finalTemplate = FileUtils.readJson(ConfigManager.get("request.folder") + templatePath);
	    }

	    // 5. Apply dynamic placeholders
	    return FileUtils.applyDataToTemplate(finalTemplate, mergedData);
	}


	
	
	public static Map<String, String> parseRequestBlock(Map<String, String> testDataRow) {

	    String requestBlock = testDataRow.get("test_data"); // your field name is test_data
	    Map<String, String> requestMap = new HashMap<>();

	    if (requestBlock != null && !requestBlock.trim().isEmpty()) {

	        String[] lines = requestBlock.split("\n");

	        for (String line : lines) {

	            line = line.trim();
	            if (line.isEmpty()) continue;

	            if (!line.contains("=")) continue;  // skip invalid lines

	            String[] parts = line.split("=", 2);
	            String key = parts[0].trim();
	            String value = parts.length > 1 ? parts[1].trim() : "";

	            requestMap.put(key, value);
	        }
	    }

	    // Merge request map into testDataRow
	    testDataRow.putAll(requestMap);

	    return testDataRow;
	}


}
