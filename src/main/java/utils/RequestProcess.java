package utils;

import java.util.Map;

public class RequestProcess {

	public static String requestBuild(String templatePath, String initialCSV, Map<String, String> testDataRow) {

		Map<String, String> baseData = CsvUtils.readCsvAsMap(initialCSV);
		Map<String, String> merged = TestDataMerger.merge(baseData, testDataRow);

		String template = FileUtils.readJson(templatePath);
		return FileUtils.applyDataToTemplate(template, merged);
	}

}
