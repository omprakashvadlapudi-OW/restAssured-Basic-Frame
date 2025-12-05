package base;

import java.util.HashMap;
import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import reportManager.AllureManager;

import static io.restassured.RestAssured.given;

public class APIClient {
	
	public Map<String, Object> headers = new HashMap<>();

	public Response post(String endpoint, Object body, Map<String, Object> headers) {
	    Response response = given()
	            .contentType(ContentType.JSON)
	            .headers(headers)
	            .body(body)
	            .when()
	            .post(endpoint)
	            .then()
	            .extract()
	            .response();

	    AllureManager.logRequestAndResponse(endpoint, "POST", body, response);

	    return response;
	}
	
	public Response delete(String endpoint, Object body, Map<String, Object> headers) {
	    Response response = given()
	            .contentType(ContentType.JSON)
	            .headers(headers)
	            .body(body)
	            .when()
	            .delete(endpoint)
	            .then()
	            .extract()
	            .response();

	    AllureManager.logRequestAndResponse(endpoint, "POST", body, response);

	    return response;
	}

    

}

