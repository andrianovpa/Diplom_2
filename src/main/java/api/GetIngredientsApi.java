package api;

import base.BaseHttpsClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CreateOrder;
import model.GetIngredients;

public class GetIngredientsApi extends BaseHttpsClient {
    private final String apiPath = "/api/ingredients";

    @Step("Отправляется запрос на получение списка ингредиентов")
    public Response getIngredients(GetIngredients getIngredients) {
        return doGetRequest(apiPath);

    }
}
