package base;

import utils.FileUtils;
import utils.PropertyReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import api.AuthAPI;

public class BaseTest {

	public String baseUrl;
	public String contentType;
	public String tenantId;
	public String accept;

	public String loginUrl;
	public String addEmpUrl;
	public String delEmpUrl;

	public String loginRequestJson;
	public String addEmpRequestJson;
	public String delEmpRequestJson;

	public String initialEmployeeCsv;

	public String token;

	AuthAPI auth = new AuthAPI();

	@BeforeSuite
	public void setup() {
		baseUrl = PropertyReader.get("base.url");

		contentType = PropertyReader.get("header.contentType");
		tenantId = PropertyReader.get("header.tenant.id");
		accept = PropertyReader.get("header.accept");

		loginUrl = baseUrl + PropertyReader.get("url.login");
		addEmpUrl = baseUrl + PropertyReader.get("url.addEmployee");
		delEmpUrl = baseUrl + PropertyReader.get("url.deleteEmployee");

		loginRequestJson = PropertyReader.get("login.request");
		addEmpRequestJson = PropertyReader.get("employee.add.request");
		delEmpRequestJson = PropertyReader.get("employee.delete.request");

		initialEmployeeCsv = PropertyReader.get("initialCsv.employee");
	}

	@BeforeMethod
	public void login() {
		token = auth.login(loginUrl, FileUtils.readJson(loginRequestJson));
	}

}
