package api;

import base.BaseHttpsClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class GetOrdersUserApi extends BaseHttpsClient {
    private final String apiPath = "/api/orders";
    @Step("Отправляется запрос на получение заказа пользователя")
    public Response getOrdersUser(String token) {
        return doGetRequest(apiPath, token);
    }

}
