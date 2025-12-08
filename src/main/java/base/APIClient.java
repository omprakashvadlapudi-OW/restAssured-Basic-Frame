package base;

import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import reportManager.AllureManager;

import static io.restassured.RestAssured.given;

public class APIClient {

	private Response executeRequest(String method, String endpoint, Object body, Map<String, Object> headers,
			ContentType contentType) {
		try {
			RequestSpecification spec = given()
					.contentType(contentType)
					.headers(headers);

			if (body != null) {
				spec.body(body);
			}

			Response response = spec.request(method, endpoint);
			AllureManager.logRequestAndResponse(endpoint, method, body, response);

			return response;
		} catch (Exception e) {
			throw new RuntimeException("Failed to execute " + method + " request to " + endpoint, e);
		}
	}

	public Response get(String endpoint, Map<String, Object> headers) {
		return executeRequest("GET", endpoint, null, headers, ContentType.JSON);
	}

	public Response get(String endpoint, Map<String, Object> headers, Map<String, Object> queryParams) {
		try {
			Response response = given()
					.contentType(ContentType.JSON)
					.headers(headers)
					.queryParams(queryParams)
					.when()
					.get(endpoint)
					.then()
					.extract()
					.response();

			AllureManager.logRequestAndResponse(endpoint, "GET", queryParams, response);
			return response;
		} catch (Exception e) {
			throw new RuntimeException("Failed to execute GET request to " + endpoint, e);
		}
	}

	public Response post(String endpoint, Object body, Map<String, Object> headers) {
		return executeRequest("POST", endpoint, body, headers, ContentType.JSON);
	}

	public Response post(String endpoint, Object body, Map<String, Object> headers, ContentType contentType) {
		return executeRequest("POST", endpoint, body, headers, contentType);
	}

	public Response put(String endpoint, Object body, Map<String, Object> headers) {
		return executeRequest("PUT", endpoint, body, headers, ContentType.JSON);
	}

	public Response put(String endpoint, Object body, Map<String, Object> headers, ContentType contentType) {
		return executeRequest("PUT", endpoint, body, headers, contentType);
	}

	public Response patch(String endpoint, Object body, Map<String, Object> headers) {
		return executeRequest("PATCH", endpoint, body, headers, ContentType.JSON);
	}

	public Response patch(String endpoint, Object body, Map<String, Object> headers, ContentType contentType) {
		return executeRequest("PATCH", endpoint, body, headers, contentType);
	}

	public Response delete(String endpoint, Object body, Map<String, Object> headers) {
		return executeRequest("DELETE", endpoint, body, headers, ContentType.JSON);
	}

	public Response delete(String endpoint, Map<String, Object> headers) {
		return executeRequest("DELETE", endpoint, null, headers, ContentType.JSON);
	}

}
