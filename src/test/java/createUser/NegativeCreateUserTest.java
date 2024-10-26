package createUser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateUser;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import user.CreateUserApi;
import user.DeleteUserApi;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class NegativeCreateUserTest {
    private static String email;
    private static String password;
    private static String name;
    private static String error;
    private static String accessToken;

    public NegativeCreateUserTest(String email, String password, String name, String error) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.error = error;

    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"andrianovpa@gmail.com", "12345678", "Pavel", "User already exists"},
                {null, "12345678", "Pavel", "Email, password and name are required fields"},
                {"andrianovpa@gmail.com", null, "Pavel", "Email, password and name are required fields"},
                {"andrianovpa@gmail.com", "12345678", null, "Email, password and name are required fields"}
        };
    }

    @BeforeClass
    public static void createUserForTest() {
        CreateUserApi createUserApi = new CreateUserApi();
        CreateUser createUser = new CreateUser("andrianovpa@gmail.com", "12345678", "Pavel");
        accessToken = createUserApi.createUser(createUser).then().extract().path("accessToken");
    }

    @AfterClass
    public static void deleteUser() {


        DeleteUserApi deleteUserApi = new DeleteUserApi();
        deleteUserApi.deleteUser(accessToken);

    }


    @Test
    @DisplayName("Негативная проверка создания пользователя")
    @Description("Направялется запрос на создание пользователя")

    public void negativeCreateUserTest() {

        CreateUserApi createUserApi = new CreateUserApi();
        CreateUser createUser = new CreateUser(email, password, name);
        createUserApi.createUser(createUser).then().statusCode(403).assertThat().body("success", equalTo(false)).body("message", equalTo(error));
    }
}