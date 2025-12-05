package dataProvider;

import utils.CsvUtils;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;


import java.util.Map;
import java.util.List;

public class EmployeeDP {
	@DataProvider(name = "employeeData")
	public static Object[][] employeeDataProvider(ITestContext context) {

	    String csvFile = context.getCurrentXmlTest().getParameter("dataset");

	    if (csvFile == null || csvFile.isEmpty()) {
	        throw new RuntimeException("Missing 'dataset' parameter in testng.xml");
	    }

	    String path = EmployeeDP.class.getClassLoader()
	            .getResource("data/tests/" + csvFile)
	            .getPath();

	    List<Map<String, String>> rows = CsvUtils.readCsvAsList(path);
	    Object[][] data = new Object[rows.size()][1];

	    for (int i = 0; i < rows.size(); i++) {
	        data[i][0] = rows.get(i);
	    }

	    return data;
	}



}
