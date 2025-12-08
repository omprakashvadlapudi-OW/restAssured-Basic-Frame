package base;

import org.testng.asserts.SoftAssert;
import io.restassured.response.Response;

public class SoftValidator {

    private static SoftAssert softAssert = new SoftAssert();

    public static void init() {
        softAssert = new SoftAssert();
    }

    public static void verifyStatusCode(Response response, int expectedStatus) {
        softAssert.assertEquals(response.statusCode(), expectedStatus,
                "Status code mismatch");
    }

    public static void verifyField(Response response, String jsonPath, Object expected) {
        Object actual = response.jsonPath().get(jsonPath);
        softAssert.assertEquals(actual, expected,
                "Field value mismatch for path: " + jsonPath);
    }

    public static void verifyContains(Response response, String jsonPath, Object value) {
        softAssert.assertTrue(
                response.jsonPath().getList(jsonPath).contains(value),
                "List does not contain expected value: " + value
        );
    }

    public static void verifyNotNull(Response response, String jsonPath) {
        softAssert.assertNotNull(response.jsonPath().get(jsonPath),
                "Field should not be null: " + jsonPath);
    }

    public static void verifyNull(Response response, String jsonPath) {
        softAssert.assertNull(response.jsonPath().get(jsonPath),
                "Field should be null: " + jsonPath);
    }

    public static void verifyResponseTime(Response response, long maxTimeMs) {
        long responseTime = response.getTime();
        softAssert.assertTrue(responseTime <= maxTimeMs,
                "Response time exceeded limit: " + responseTime + "ms");
    }

    public static void verifyContentType(Response response, String expectedContentType) {
        String actualContentType = response.getContentType();
        softAssert.assertTrue(actualContentType.contains(expectedContentType),
                "Content-Type mismatch. Expected contains: " + expectedContentType);
    }

    public static void verifyHeader(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        softAssert.assertEquals(actualValue, expectedValue,
                "Header '" + headerName + "' mismatch");
    }

    public static void verifyListSize(Response response, String jsonPath, int expectedSize) {
        int actualSize = response.jsonPath().getList(jsonPath).size();
        softAssert.assertEquals(actualSize, expectedSize,
                "List size mismatch for path: " + jsonPath);
    }

    public static void verifyPattern(Response response, String jsonPath, String regex) {
        String value = response.jsonPath().getString(jsonPath);
        softAssert.assertTrue(
                value != null && value.matches(regex),
                "Field does not match pattern for path: " + jsonPath);
    }

    public static void verifyBodyContains(Response response, String expectedText) {
        String body = response.getBody().asString();
        softAssert.assertTrue(body.contains(expectedText),
                "Response body does not contain expected text");
    }

    public static void assertAll() {
        softAssert.assertAll();
    }
}
