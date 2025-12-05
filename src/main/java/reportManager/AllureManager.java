package reportManager;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

public class AllureManager {

	public static void logRequestAndResponse(String endpoint, String method, Object body, Response response) {

		Allure.addAttachment("API Request - " + method,
				"URL: " + endpoint + "\n\n" + "Body:\n" + (body != null ? body.toString() : "No Body"));

		Allure.addAttachment("API Response - " + method, "application/json", response.getBody().asPrettyString());
	}
}
