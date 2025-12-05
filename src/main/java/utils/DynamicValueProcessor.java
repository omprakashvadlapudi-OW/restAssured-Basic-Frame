package utils;

import java.time.LocalDate;
import java.util.*;

public class DynamicValueProcessor {

	public static String process(String value) {

		try {

			if (value.startsWith("RANDOM(")) {
				return DataGenerator.getRandomNumber(10000, 100000) + "";
			}

			if (value.equalsIgnoreCase("UUID()")) {
				return UUID.randomUUID().toString();
			}

			if (value.equalsIgnoreCase("TODAY()")) {
				return LocalDate.now().toString();
			}

			if (value.startsWith("RANDOM_EMAIL(")) {
				return DataGenerator.getRandomEmail();
			}

		} catch (Exception ignored) {
		}

		return value;
	}

}
