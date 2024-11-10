package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class BaseHttpsClient {
    private RequestSpecification baseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(URL.PLACEHOLDER_HOST)
                .addHeader("Content-type", "application/json")
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }

    protected Response doGetRequest(String path) {
        return given()
                .spec(baseRequestSpec())
                .get(path)
                .thenReturn();
    }
    protected Response doGetRequest(String path, String token) {
        return given()
                .spec(baseRequestSpec())
                .header("Authorization", token)
                .get(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, Object body) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .post(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, Object body, String token) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .header("Authorization", token)
                .post(path)
                .thenReturn();
    }
    protected Response doPostRequest(String path, List<String> body, String token) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .header("Authorization", token)
                .post(path)
                .thenReturn();
    }
    protected Response doPostRequest(String path, List<String> body) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .post(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, String token) {
        return given()
                .spec(baseRequestSpec())
                .header("Authorization", token)
                .post(path)
                .thenReturn();
    }

    protected Response doDeleteRequest(String path, String token) {
        return given()
                .spec(baseRequestSpec())
                .header("Authorization", token)
                .delete(path)
                .thenReturn();
    }

    protected Response doPatchRequest(String path, Object body, String token) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .header("Authorization", token)
                .patch(path)
                .thenReturn();
    }

    protected Response doPatchRequestWithoutAuth(String path, Object body) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .patch(path)
                .thenReturn();
    }
}
