package tests;

import base.BaseTest;
import base.Validator;
import dataProvider.EmployeeDP;
import api.EmployeeAPI;
import io.restassured.response.Response;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.*;

import java.io.IOException;
import java.util.Map;

public class AddEmployeeTest extends BaseTest {

	EmployeeAPI employeeAPI = new EmployeeAPI();

	String createdEmpId;

	@Test(dataProvider = "employeeData", dataProviderClass = EmployeeDP.class)
	public void addEmployee(Map<String, String> row) throws IOException {
		String request = RequestProcess.requestBuild(addEmpRequestJson, initialEmployeeCsv, row);
		Response res = employeeAPI.addEmployee(addEmpUrl, token, request);
		createdEmpId = FileUtils.getValue(request, "id");
		Map<String, String> expected = ResponseProcess.parseResponseExpectations(row.get("response"));
		Validator.verifyField(res, "message", expected.get("codeMessage"));

	}

	@AfterMethod
	void deleteEmployeeAfterTest() throws IOException {
		if (createdEmpId != null) {
			Map<String, String> data = Map.of("id", createdEmpId);
			Response delResponse = employeeAPI.deleteEmployee(delEmpUrl, token,
					FileUtils.applyDataToTemplate(FileUtils.readJson(delEmpRequestJson), data));
			Validator.verifyField(delResponse, "message", "success");
		}
	}
}
