package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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

    protected Response doPostRequest(String path, Object body) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .post(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, Object body, Map<String, String> params) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .params(params)
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
}
