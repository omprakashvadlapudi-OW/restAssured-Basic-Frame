package utils;

import java.util.Random;

public class DataGenerator {

	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";

	public static int getRandomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}

	public static String getRandomEmail() {

		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
		}

		return sb.toString() + "@example.com";
	}

}
