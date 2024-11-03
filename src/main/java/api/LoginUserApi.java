package api;

import base.BaseHttpsClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.LoginUser;

public class LoginUserApi extends BaseHttpsClient {
    private final String apiPath = "/api/auth/login";

    @Step("Отправка запроса на авторизацию(получение авторизационного токена)")
    public Response loginUser(LoginUser loginUser) {
        return doPostRequest(apiPath, loginUser);
    }
}
