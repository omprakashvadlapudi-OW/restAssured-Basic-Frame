package utils;

import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;


public class CsvUtils {


	public static List<Map<String, String>> readCsvListFromClasspath(String resourcePath) {
		List<Map<String, String>> allRows = new ArrayList<>();

		try (InputStream is = CsvUtils.class.getClassLoader().getResourceAsStream(resourcePath);
				InputStreamReader isr = new InputStreamReader(is);
				CSVReader reader = new CSVReader(isr)) {

			if (is == null) {
				throw new RuntimeException("CSV resource not found in classpath: " + resourcePath);
			}

			String[] headers = reader.readNext();
			if (headers == null) {
				throw new RuntimeException("CSV file is empty: " + resourcePath);
			}

			String[] line;
			while ((line = reader.readNext()) != null) {
				Map<String, String> row = new HashMap<>();
				for (int i = 0; i < headers.length && i < line.length; i++) {
					row.put(headers[i].trim(), line[i]);
				}
				allRows.add(row);
			}

		} catch (Exception e) {
			throw new RuntimeException("Error reading CSV resource: " + resourcePath, e);
		}

		return allRows;
	}

	public static Map<String, String> readCsvFromClasspath(String resourcePath) {
		Map<String, String> data = new HashMap<>();

		try (InputStream is = CsvUtils.class.getClassLoader().getResourceAsStream(resourcePath);
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			if (is == null) {
				throw new RuntimeException("CSV resource not found in classpath: " + resourcePath);
			}

			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty() || line.startsWith("#"))
					continue;

				String[] parts = line.split(",", 2);
				if (parts.length < 2)
					continue;

				data.put(parts[0].trim(), parts[1].trim());
			}

		} catch (Exception e) {
			throw new RuntimeException("Error reading CSV resource: " + resourcePath, e);
		}

		return data;
	}
}
