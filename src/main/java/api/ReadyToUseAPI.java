package api;

import base.APIClient;
import base.HeaderBuilder;
import io.restassured.response.Response;

public class ReadyToUseAPI extends APIClient {

    public Response createObject(String url, String payload) {
        return post(url, payload, HeaderBuilder.defaultHeaders());
    }

    public Response getObject(String url, String objectId) {
        return get(url + "/" + objectId, HeaderBuilder.defaultHeaders());
    }

    public Response deleteObject(String url, String objectId) {
        return delete(url + "/" + objectId, null, HeaderBuilder.defaultHeaders());
    }
}
