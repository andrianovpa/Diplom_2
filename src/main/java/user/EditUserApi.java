package user;

import base.BaseHttpsClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.EditUser;

public class EditUserApi extends BaseHttpsClient {
    private final String apiPath = "/api/auth/user";

    @Step("Отправка запроса на изменение пользователя")
    public Response editUser(EditUser editUser, String token) {
        return doPatchRequest(apiPath, editUser, token);
    }
}
