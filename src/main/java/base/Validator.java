package base;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import io.restassured.response.Response;

public class Validator {

	public static void verifyStatusCode(Response response, int expectedStatus) {
		MatcherAssert.assertThat(response.statusCode(), Matchers.equalTo(expectedStatus));
	}

	public static void verifyField(Response response, String jsonPath, Object expected) {
		MatcherAssert.assertThat(response.jsonPath().get(jsonPath), Matchers.equalTo(expected));
	}

	public static void verifyContains(Response response, String jsonPath, Object value) {
		MatcherAssert.assertThat(response.jsonPath().getList(jsonPath), Matchers.hasItem(value));
	}
}
