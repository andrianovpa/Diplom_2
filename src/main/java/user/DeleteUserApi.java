package user;

import base.BaseHttpsClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.DeleteUser;

public class DeleteUserApi extends BaseHttpsClient {
    private final String apiPath = "api/auth/user";
    @Step("Отправка запроса на удаление пользователя")
public Response deleteUser(String token) {
    return doDeleteRequest(apiPath, token);
    }
}
