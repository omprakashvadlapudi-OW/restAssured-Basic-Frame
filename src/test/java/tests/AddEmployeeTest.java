package tests;

import base.BaseTest;
import base.SoftValidator;
import dataProvider.EmployeeDP;
import api.AuthAPI;
import api.EmployeeAPI;
import io.restassured.response.Response;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.*;

import java.io.IOException;
import java.util.Map;

public class AddEmployeeTest extends BaseTest {

	EmployeeAPI employeeAPI = new EmployeeAPI();
	AuthAPI auth=new AuthAPI();
	String createdEmpId;

	@BeforeTest
	public void login() {
		token = auth.login(loginUrl, FileUtils.readJson(loginRequestJson));
	}

	@Test(dataProvider = "employeeData", dataProviderClass = EmployeeDP.class)
	public void addEmployee(Map<String, String> row) throws IOException {
		String request = TemplateEngine.process(addEmpRequestJson, initialEmployeeCsv, row);
		Response res = employeeAPI.addEmployee(addEmpUrl, token, request);
		createdEmpId = FileUtils.getValue(request, "id");
		Map<String, String> expected = ResponseProcess.parseResponseExpectations(row.get("response"));
		SoftValidator.verifyField(res, "message", expected.get("codeMessage"));
	}

	@AfterMethod
	void deleteEmployeeAfterTest() throws IOException {
		if (createdEmpId != null) {
			Map<String, String> data = Map.of("id", createdEmpId);
			Response delResponse = employeeAPI.deleteEmployee(delEmpUrl, token,
					FileUtils.applyDataToTemplate(FileUtils.readJson(delEmpRequestJson), data));
			SoftValidator.verifyField(delResponse, "message", "success");
		}
	}
}
