package login;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateUser;
import model.LoginUser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import api.CreateUserApi;
import api.DeleteUserApi;
import api.LoginUserApi;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;


public class PositiveLoginTest {
    private static String email = "andrianovpa@gmail.com";
    private static String password = "12345678";
    private static String name = "Pavel";
    private static String accessToken;


    @BeforeClass
    public static void createUserForTest() {
        CreateUserApi createUserApi = new CreateUserApi();
        CreateUser createUser = new CreateUser(email, password, name);
        accessToken = createUserApi.createUser(createUser).then().extract().path("accessToken");
    }

    @AfterClass
    public static void deleteUserForTest() {
        DeleteUserApi deleteUserApi = new DeleteUserApi();
        deleteUserApi.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Положительная проверка авторизации пользователя")
    @Description("Отправка запроса методом post на успешную авторизацию в системе с валидными данными")
    public void positiveLoginTest() {
        LoginUserApi loginUserApi = new LoginUserApi();
        LoginUser loginUser = new LoginUser(email, password);
        loginUserApi.loginUser(loginUser)
                .then().statusCode(200).assertThat()
                .body("success", equalTo(true))
                .body("accessToken", containsString("Bearer "))
                .body("refreshToken", notNullValue())
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));

    }
}

