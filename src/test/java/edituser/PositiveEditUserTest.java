package edituser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateUser;
import model.EditUser;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import api.CreateUserApi;
import api.DeleteUserApi;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import api.EditUserApi;

import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class PositiveEditUserTest {
    private static String email;
    private static String password;
    private static String name;
    private static String accessToken;

    public PositiveEditUserTest(String email, String password, String name) {
        this.password = password;
        this.email = email;
        this.name = name;

    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"andrianovpa1@gmail.com", "123456781", "Pavel1"},
                {"andrianovpa1@gmail.com", "12345678", "Pavel"},
                {"andrianovpa@gmail.com", "123456781", "Pavel"},
                {"andrianovpa@gmail.com", "12345678", "Pavel1"},
                {"andrianovpa1@gmail.com", "123456781", "Pavel"},
                {"andrianovpa1@gmail.com", "12345678", "Pavel1"},
                {"andrianovpa@gmail.com", "123456781", "Pavel1"}
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
    @DisplayName("Позитивная проверка обновления пользователя")
    @Description("Направялется запрос на обновление существующего пользователя")

    public void editUserTest() {

        EditUserApi editUserApi = new EditUserApi();
        EditUser editUser = new EditUser(email, password, name);
        editUserApi.editUser(editUser, accessToken)
                .then().statusCode(200)
                .assertThat()
                .body("success", equalTo(true))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));
    }
}