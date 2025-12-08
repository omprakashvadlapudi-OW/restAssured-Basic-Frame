package dataProvider;

import utils.CsvUtils;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.util.Map;
import java.util.List;

public class EmployeeDP {

	private static final String DEFAULT_DATASET = "employee_new_test.csv";

	@DataProvider(name = "employeeData")
	public static Object[][] employeeDataProvider(ITestContext context) {

		String csvFile = context.getCurrentXmlTest().getParameter("dataset");

		if (csvFile == null || csvFile.isEmpty()) {
			System.out.println("No 'dataset' parameter found in testng.xml, using default: " + DEFAULT_DATASET);
			csvFile = DEFAULT_DATASET;
		}

		String resourcePath = "AddEmployee/TestData/" + csvFile;
		List<Map<String, String>> rows = CsvUtils.readCsvListFromClasspath(resourcePath);

		Object[][] data = new Object[rows.size()][1];

		for (int i = 0; i < rows.size(); i++) {
			data[i][0] = rows.get(i);
		}

		return data;
	}

}
