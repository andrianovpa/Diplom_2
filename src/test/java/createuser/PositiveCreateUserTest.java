package createuser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateUser;
import org.junit.After;
import org.junit.Test;
import api.CreateUserApi;
import api.DeleteUserApi;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;


public class PositiveCreateUserTest {
    private static String email = "andrianovpa@gmail.com";
    private static String password = "12345678";
    private static String name = "Pavel";
    private static String accessToken;

    @After
    public void deleteUser() {

        if (accessToken != null) {
            DeleteUserApi deleteUserApi = new DeleteUserApi();
            deleteUserApi.deleteUser(accessToken);
        }
    }


    @Test
    @DisplayName("Положительная проверка создания пользователя") // имя теста
    @Description("Направялется запрос на создание пользователя с валидными данными") // описание теста
    public void positiveCreateUserTest() {

        CreateUserApi createUserApi = new CreateUserApi();
        CreateUser createUser = new CreateUser(email, password, name);
        accessToken = createUserApi.createUser(createUser)
                .then().statusCode(200).assertThat()
                .body("success", equalTo(true))
                .body("accessToken", containsString("Bearer "))
                .body("refreshToken", notNullValue())
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name))
                .extract().path("accessToken");
    }
}