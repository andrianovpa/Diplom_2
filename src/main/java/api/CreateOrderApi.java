package api;

import base.BaseHttpsClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CreateOrder;
import model.GetIngredients;

public class CreateOrderApi extends BaseHttpsClient {
    private final String apiPath = "/api/orders";

        @Step("Отправляется запрос на создание заказа")
    public Response createOrder(GetIngredients getIngredients, String token){
        return doPostRequest(apiPath, getIngredients, token);
        }
    @Step("Отправляется запрос на создание заказа, без авторизационного токена")
    public Response createOrderWithoutAuth(GetIngredients getIngredients){
        return doPostRequest(apiPath, getIngredients);
    }
    @Step("Отправляется запрос на создание заказа")
    public Response createOrderWithoutIndgredients(String token){
        return doPostRequest(apiPath, token);
    }
}
