package user;


import base.BaseHttpsClient;
import model.CreateUser;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CreateUserApi extends BaseHttpsClient {
    private final String apiPath = "/api/auth/register";

    @Step("Отправляется запрос на создание пользователя")
    public Response createUser(CreateUser courier) {
        return doPostRequest(apiPath, courier);
    }
}
