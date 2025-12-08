package base;

import config.ConfigManager;
import org.testng.annotations.BeforeSuite;


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
	public String requestFolder;
	public String csvFolder;
	
	public String readyToUse;


	@BeforeSuite
	public void setup() {
		baseUrl = ConfigManager.get("base.url");

		contentType = ConfigManager.get("header.contentType");
		tenantId = ConfigManager.get("header.tenant.id");
		accept = ConfigManager.get("header.accept");

		loginUrl = baseUrl + ConfigManager.get("url.login");
		addEmpUrl = baseUrl + ConfigManager.get("url.addEmployee");
		delEmpUrl = baseUrl + ConfigManager.get("url.deleteEmployee");

		loginRequestJson = ConfigManager.get("login.request");
		addEmpRequestJson = ConfigManager.get("employee.add.request");
		delEmpRequestJson = ConfigManager.get("employee.delete.request");

		initialEmployeeCsv = ConfigManager.get("initialCsv.employee");

		requestFolder = ConfigManager.get("request.folder");
		csvFolder = ConfigManager.get("testCsv.folder");
		
		
		readyToUse=ConfigManager.get("post.url");

		
	}

}
