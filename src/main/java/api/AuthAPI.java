package api;

import base.APIClient;
import base.HeaderBuilder;
import io.restassured.response.Response;

public class AuthAPI extends APIClient {

	public String login(String url, String payload) {
		Response res = post(url, payload, HeaderBuilder.defaultHeaders());
		return res.jsonPath().getString("token");
	}
}
