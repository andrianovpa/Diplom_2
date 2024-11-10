package edituser;

import api.CreateUserApi;
import api.DeleteUserApi;
import api.EditUserApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateUser;
import model.EditUser;
import model.RandomData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class NegativeEditUserTest {
    private static String randomEmail = RandomData.randomEmail();
    private static String randomPassword = RandomData.randomPassword(8);
    private static String randomName = RandomData.randomName();
    private static String accessToken;
    private static String existEmail = RandomData.randomEmail();
    private static String existPassword = RandomData.randomPassword(8);
    private static String existName = RandomData.randomName();
    private static String ExistAccessToken;


    @Before
    public void createUserForTest() {
        CreateUserApi createUserApi = new CreateUserApi();
        CreateUser createUser = new CreateUser(randomEmail, randomPassword, randomName);
        accessToken = createUserApi.createUser(createUser).then().extract().path("accessToken");
        CreateUser existCreateUser = new CreateUser(existEmail, existPassword, existName);
        ExistAccessToken = createUserApi.createUser(existCreateUser).then().extract().path("accessToken");
    }

    @After
    public void deleteUser() {


        DeleteUserApi deleteUserApi = new DeleteUserApi();
        deleteUserApi.deleteUser(accessToken);
        deleteUserApi.deleteUser(ExistAccessToken);

    }


    @Test
    @DisplayName("Негативная проверка обновления пользователя, без авторизации")
    @Description("Направялется запрос на обновление существующего пользователя, без авторизационного токена")

    public void editUserWithoutAuthTest() {

        EditUserApi editUserApi = new EditUserApi();
        EditUser editUser = new EditUser(randomEmail, randomPassword, randomName);
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
        EditUser editUser = new EditUser(existEmail, existPassword, existName);
        editUserApi.editUser(editUser, accessToken)
                .then().statusCode(403)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("User with such email already exists"));
    }
}