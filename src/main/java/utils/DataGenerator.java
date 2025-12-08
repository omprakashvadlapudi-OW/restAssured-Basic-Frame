package utils;

import java.util.Random;


public class DataGenerator {

	private static final Random RANDOM = new Random();
	private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


	public static int getRandomNumber(int min, int max) {
		return RANDOM.nextInt(max - min + 1) + min;
	}

	public static String getRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
		}
		return sb.toString();
	}


	public static String getRandomEmail(String domain) {
		String username = getRandomString(10).toLowerCase();
		return username + "@" + domain;
	}
}
