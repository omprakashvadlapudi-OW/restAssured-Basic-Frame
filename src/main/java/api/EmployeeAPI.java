package api;

import base.APIClient;
import base.HeaderBuilder;
import config.ConfigManager;
import io.restassured.response.Response;

public class EmployeeAPI extends APIClient {
	private String tenantId = ConfigManager.get("header.tenant.id");
	private String accept = ConfigManager.get("header.accept");

	public Response addEmployee(String url, String token, String payload) {
		return post(url, payload, HeaderBuilder.tokenHeaders(token, tenantId, accept));
	}

	public Response deleteEmployee(String url, String token, String payload) {
		return delete(url, payload, HeaderBuilder.tokenHeaders(token, tenantId));
	}
}
