package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reportManager.ExtentManager;


public class ExtentReportListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.initReports();
        System.out.println("Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flushReports();
        System.out.println("Test Suite Finished: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        String className = result.getTestClass().getName();

        if (description == null || description.isEmpty()) {
            description = "Test: " + testName;
        }

        ExtentManager.createTest(testName, description, className);
        ExtentManager.logInfo("Test started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.logPass("Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentManager.logFail("Test failed: " + result.getMethod().getMethodName());
        ExtentManager.logFail("Error: " + result.getThrowable().getMessage());

        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement element : result.getThrowable().getStackTrace()) {
            stackTrace.append(element.toString()).append("\n");
        }
        ExtentManager.logFail("<pre>" + stackTrace.toString() + "</pre>");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.logSkip("Test skipped: " + result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            ExtentManager.logSkip("Reason: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ExtentManager.logWarning("Test failed but within success percentage: " + result.getMethod().getMethodName());
    }
}
