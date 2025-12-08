package tests;

import base.BaseTest;
import base.Validator;
import dataProvider.ReadyToUseDP;
import api.ReadyToUseAPI;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.*;
import java.util.Map;

public class ReadyToUseTest extends BaseTest {

    ReadyToUseAPI readyToUseAPI = new ReadyToUseAPI();
    String createdObjectId;

    @Test(dataProvider = "readyToUseData", dataProviderClass = ReadyToUseDP.class)
    public void createObject(Map<String, String> row) {
        Map<String, String> testData = ResponseProcess.parseResponseExpectations(row.get("test_data"));

        String requestTemplate = FileUtils.readJson("src/test/resources/ReadyToUse/Request/readyToUseRequest.json");
        String request = FileUtils.applyDataToTemplate(requestTemplate, testData);

        Response res = readyToUseAPI.createObject(readyToUse, request);

        createdObjectId = res.jsonPath().getString("id");

        Map<String, String> expected = ResponseProcess.parseResponseExpectations(row.get("response"));

        int expectedStatus = Integer.parseInt(expected.get("status"));
        Validator.verifyStatusCode(res, expectedStatus);
        Validator.verifyField(res, "name", testData.get("name"));
        Validator.verifyNotNull(res, "id");
        Validator.verifyNotNull(res, "createdAt");
    }

    @AfterMethod
    void deleteObjectAfterTest() {
        if (createdObjectId != null) {
            readyToUseAPI.deleteObject(readyToUse, createdObjectId);
            System.out.println("Deleted object with ID: " + createdObjectId);
        }
    }
}
