package base;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import io.restassured.response.Response;

public class Validator {

	public static void verifyStatusCode(Response response, int expectedStatus) {
		MatcherAssert.assertThat("Status code mismatch", response.statusCode(), Matchers.equalTo(expectedStatus));
	}

	public static void verifyField(Response response, String jsonPath, Object expected) {
		MatcherAssert.assertThat("Field value mismatch for path: " + jsonPath, response.jsonPath().get(jsonPath),
				Matchers.equalTo(expected));
	}

	public static void verifyContains(Response response, String jsonPath, Object value) {
		MatcherAssert.assertThat("List does not contain expected value", response.jsonPath().getList(jsonPath),
				Matchers.hasItem(value));
	}

	public static void verifyNotNull(Response response, String jsonPath) {
		MatcherAssert.assertThat("Field should not be null: " + jsonPath, response.jsonPath().get(jsonPath),
				Matchers.notNullValue());
	}

	public static void verifyNull(Response response, String jsonPath) {
		MatcherAssert.assertThat("Field should be null: " + jsonPath, response.jsonPath().get(jsonPath),
				Matchers.nullValue());
	}

	public static void verifyResponseTime(Response response, long maxTimeMs) {
		long responseTime = response.getTime();
		MatcherAssert.assertThat("Response time exceeded limit", responseTime, Matchers.lessThanOrEqualTo(maxTimeMs));
	}

	public static void verifyContentType(Response response, String expectedContentType) {
		String actualContentType = response.getContentType();
		MatcherAssert.assertThat("Content-Type mismatch", actualContentType,
				Matchers.containsString(expectedContentType));
	}

	public static void verifyHeader(Response response, String headerName, String expectedValue) {
		String actualValue = response.getHeader(headerName);
		MatcherAssert.assertThat("Header '" + headerName + "' mismatch", actualValue, Matchers.equalTo(expectedValue));
	}

	public static void verifyListSize(Response response, String jsonPath, int expectedSize) {
		int actualSize = response.jsonPath().getList(jsonPath).size();
		MatcherAssert.assertThat("List size mismatch for path: " + jsonPath, actualSize,
				Matchers.equalTo(expectedSize));
	}

	public static void verifyPattern(Response response, String jsonPath, String regex) {
		String value = response.jsonPath().getString(jsonPath);
		MatcherAssert.assertThat("Field does not match pattern: " + jsonPath, value, Matchers.matchesPattern(regex));
	}

	public static void verifyBodyContains(Response response, String expectedText) {
		String body = response.getBody().asString();
		MatcherAssert.assertThat("Response body does not contain expected text", body,
				Matchers.containsString(expectedText));
	}
}
