package login;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateUser;
import model.LoginUser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import api.CreateUserApi;
import api.DeleteUserApi;
import api.LoginUserApi;

import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class NegativeLoginTest {
    private static String email = "andrianovpa@gmail.com";
    private static String password = "12345678";
    private static String name = "Pavel";
    private String notValidEmail;
    private String notValidPassword;
    private static String accessToken;

    public NegativeLoginTest(String notValidEmail, String notValidPassword) {
        this.notValidEmail = notValidEmail;
        this.notValidPassword = notValidPassword;

    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"andrianovpa777@gmail.com", "123456789"},
                {"andrianovpa777@gmail.com", null},
                {"andrianovpa777@gmail.com", password},
                {null, "123456789"},
                {email, "123456789"},
                {null, null}


        };
    }

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
    @DisplayName("Негативная проверка авторизации пользователя")
    @Description("Отправка запроса методом post на успешную авторизацию в системе с не валидными данными")
    public void negativeLoginTest() {
        LoginUserApi loginUserApi = new LoginUserApi();
        LoginUser loginUser = new LoginUser(notValidEmail, notValidPassword);
        loginUserApi.loginUser(loginUser)
                .then().statusCode(401).assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
