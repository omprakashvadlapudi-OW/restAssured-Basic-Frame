package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DynamicValueProcessor {

	public static String process(String value) {
		if (value == null || !value.contains("(")) {
			return value;
		}

		if (value.equals("RANDOM()")) {
			return String.valueOf(DataGenerator.getRandomNumber(10000, 100000));
		}

		Pattern randomRange = Pattern.compile("RANDOM\\((\\d+),(\\d+)\\)");
		Matcher matcher = randomRange.matcher(value);
		if (matcher.matches()) {
			int min = Integer.parseInt(matcher.group(1));
			int max = Integer.parseInt(matcher.group(2));
			return String.valueOf(DataGenerator.getRandomNumber(min, max));
		}

		Pattern randomString = Pattern.compile("RANDOM_STRING\\((\\d+)\\)");
		matcher = randomString.matcher(value);
		if (matcher.matches()) {
			int length = Integer.parseInt(matcher.group(1));
			return DataGenerator.getRandomString(length);
		}

		if (value.startsWith("RANDOM_EMAIL")) {
			String domain = value.contains("(") ? value.substring(value.indexOf("(") + 1, value.indexOf(")"))
					: "example.com";
			return DataGenerator.getRandomEmail(domain);
		}

		if (value.equals("TIMESTAMP()")) {
			return String.valueOf(System.currentTimeMillis());
		}

		if (value.equals("UUID()")) {
			return java.util.UUID.randomUUID().toString();
		}

		return value;
	}
}
