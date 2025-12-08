package reportManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.response.Response;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static String reportPath;


    public static void initReports() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportPath = "test-output/extent-reports/ExtentReport_" + timestamp + ".html";

            File reportDir = new File("test-output/extent-reports");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("API Test Report");
            sparkReporter.config().setReportName("RestAssured API Testing");
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

            // Initialize ExtentReports
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Environment", System.getProperty("env", "QA"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));

            System.out.println("ExtentReports initialized: " + reportPath);
        }
    }


    public static void createTest(String testName, String description) {
        ExtentTest extentTest = extent.createTest(testName, description);
        test.set(extentTest);
    }


    public static void createTest(String testName, String description, String category) {
        ExtentTest extentTest = extent.createTest(testName, description);
        extentTest.assignCategory(category);
        test.set(extentTest);
    }

 
    public static ExtentTest getTest() {
        return test.get();
    }


    public static void logInfo(String message) {
        if (test.get() != null) {
            test.get().log(Status.INFO, message);
        }
    }

    public static void logPass(String message) {
        if (test.get() != null) {
            test.get().log(Status.PASS, message);
        }
    }


    public static void logFail(String message) {
        if (test.get() != null) {
            test.get().log(Status.FAIL, message);
        }
    }

    /**
     * Log warning message
     */
    public static void logWarning(String message) {
        if (test.get() != null) {
            test.get().log(Status.WARNING, message);
        }
    }


    public static void logSkip(String message) {
        if (test.get() != null) {
            test.get().log(Status.SKIP, message);
        }
    }

    public static void logRequest(String endpoint, String method, Object body) {
        if (test.get() != null) {
            test.get().info("<b>Request Details:</b>");
            test.get().info("Endpoint: " + endpoint);
            test.get().info("Method: " + method);
            if (body != null) {
                test.get().info("<pre>" + body.toString() + "</pre>");
            }
        }
    }


    public static void logResponse(Response response) {
        if (test.get() != null && response != null) {
            test.get().info("<b>Response Details:</b>");
            test.get().info("Status Code: " + response.getStatusCode());
            test.get().info("Response Time: " + response.getTime() + " ms");
            test.get().info("<pre>" + response.getBody().asPrettyString() + "</pre>");
        }
    }


    public static void logRequestAndResponse(String endpoint, String method, Object body, Response response) {
        logRequest(endpoint, method, body);
        logResponse(response);
    }


    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            System.out.println("ExtentReports generated: " + reportPath);
        }
    }


    public static String getReportPath() {
        return reportPath;
    }
}
