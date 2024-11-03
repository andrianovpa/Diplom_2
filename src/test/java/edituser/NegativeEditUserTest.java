package edituser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateUser;
import model.EditUser;
import org.junit.*;
import api.CreateUserApi;
import api.DeleteUserApi;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import api.EditUserApi;

import static org.hamcrest.core.IsEqual.equalTo;

public class NegativeEditUserTest {
    private static String email = "andrianovpa@gmail.com";
    private static String password = "12345678";
    private static String name = "Pavel";
    private static String accessToken;
    private static String email_2 = "andrianovpa1@gmail.com";
    private static String password_2 = "123456781";
    private static String name_2 = "Pavel1";
    private static String accessToken_2;


    @BeforeClass
    public static void createUserForTest() {
        CreateUserApi createUserApi = new CreateUserApi();
        CreateUser createUser = new CreateUser(email,password,name);
        accessToken = createUserApi.createUser(createUser).then().extract().path("accessToken");
        CreateUser createUser_2 = new CreateUser(email_2,password_2,name_2);
        accessToken_2 = createUserApi.createUser(createUser_2).then().extract().path("accessToken");
    }

    @AfterClass
    public static void deleteUser() {


        DeleteUserApi deleteUserApi = new DeleteUserApi();
        deleteUserApi.deleteUser(accessToken);
        deleteUserApi.deleteUser(accessToken_2);

    }


    @Test
    @DisplayName("Негативная проверка обновления пользователя, без авторизации")
    @Description("Направялется запрос на обновление существующего пользователя, без авторизационного токена")

    public void editUserWithoutAuthTest() {

        EditUserApi editUserApi = new EditUserApi();
        EditUser editUser = new EditUser(email, password, name);
        editUserApi.editUserWithoutAuth(editUser)
                .then().statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
    @Test
    @DisplayName("Негативная проверка обновления пользователя, без авторизации")
    @Description("Направялется запрос на обновление существующего пользователя, без авторизационного токена")

    public void editUserWithExistMailTest() {

        EditUserApi editUserApi = new EditUserApi();
        EditUser editUser = new EditUser(email, password, name);
        editUserApi.editUser(editUser, accessToken_2)
                .then().statusCode(403)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("User with such email already exists"));
    }
}