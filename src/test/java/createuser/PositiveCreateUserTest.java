package createuser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateUser;
import model.RandomData;
import org.junit.After;
import org.junit.Test;
import api.CreateUserApi;
import api.DeleteUserApi;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;


public class PositiveCreateUserTest {
    private static String randomEmail = RandomData.randomEmail();
    private static String randomPassword = RandomData.randomPassword(8);
    private static String randomName = RandomData.randomName();

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
        CreateUser createUser = new CreateUser(randomEmail, randomPassword, randomName);
        accessToken = createUserApi.createUser(createUser)
                .then().statusCode(200).assertThat()
                .body("success", equalTo(true))
                .body("accessToken", containsString("Bearer "))
                .body("refreshToken", notNullValue())
                .body("user.email", equalTo(randomEmail))
                .body("user.name", equalTo(randomName))
                .extract().path("accessToken");
    }
}