package utils;

import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;

public class CsvUtils {

	public static Map<String, String> readCsvAsMap(String filePath) {
		Map<String, String> data = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String line;
			while ((line = br.readLine()) != null) {

				if (line.trim().isEmpty())
					continue;
				if (line.startsWith("#"))
					continue;

				String[] parts = line.split(",", 2);

				if (parts.length < 2)
					continue;

				data.put(parts[0].trim(), parts[1].trim());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public static List<Map<String, String>> readCsvAsList(String filePath) {
		List<Map<String, String>> allRows = new ArrayList<>();

		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

			String[] headers = reader.readNext();
			if (headers == null)
				return allRows;

			String[] line;
			while ((line = reader.readNext()) != null) {

				Map<String, String> row = new HashMap<>();
				for (int i = 0; i < headers.length; i++) {
					row.put(headers[i], line[i]);
				}
				allRows.add(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return allRows;
	}

}
