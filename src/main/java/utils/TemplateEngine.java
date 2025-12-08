package utils;

import java.util.Map;

import config.ConfigManager;


public class TemplateEngine {

    public static String process(String templatePath, String baseDataPath, Map<String, String> testData) {
        String template = FileUtils.readJson(ConfigManager.get("request.folder") + templatePath);

        Map<String, String> baseData = CsvUtils.readCsvFromClasspath(baseDataPath);

        Map<String, String> mergedData = TestDataMerger.merge(baseData, testData);

        return FileUtils.applyDataToTemplate(template, mergedData);
    }


    public static String process(String templatePath, Map<String, String> testData) {
        String template = FileUtils.readJson(ConfigManager.get("request.folder") + templatePath);
        return FileUtils.applyDataToTemplate(template, testData);
    }

 
    public static String processInline(String template, Map<String, String> data) {
        return FileUtils.applyDataToTemplate(template, data);
    }
}
