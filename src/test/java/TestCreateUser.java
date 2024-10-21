import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateUser;
import model.DeleteUser;
import org.junit.After;
import org.junit.Test;
import user.CreateUserApi;
import user.DeleteUserApi;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;


public class TestCreateUser {
    private static String email = "andrianovpa@gmail.com";
    private static String password = "12345678";
    private static String name = "Pavel";
    private static String accessToken;

    @After
    public void deleteUser() {
        try {
            // Проверяем, не равен ли accessToken null
            if (accessToken != null) {
                DeleteUserApi deleteUserApi = new DeleteUserApi();
                deleteUserApi.deleteUser(accessToken);
                System.out.println("Пользователь успешно удалён");
            } else {
                System.out.println("Удаление пользователя не требуется");
            }
        } catch (Exception e) {
            // Обработка исключений
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
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

    @Test
    @DisplayName("Проверка создания пользователя ранее созданного в системе") // имя теста
    @Description("Направялется запрос на создание пользователя с валидными данными два раза и после второго раза проверяется код и тело ответа") // описание теста
    public void negativeCreateUserTest() {

        CreateUserApi createUserApi = new CreateUserApi();
        CreateUser createUser = new CreateUser(email, password, name);
        accessToken = createUserApi.createUser(createUser).then().extract().path("accessToken");
        createUserApi.createUser(createUser)
                .then().statusCode(403).assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

@Test
@DisplayName("Проверка создания пользователя ,без обязательного поля") // имя теста
@Description("Направялется запрос на создание пользователя без тега name") // описание теста
public void negativeCreateUserWithoutFieldTest() {

    CreateUserApi createUserApi = new CreateUserApi();
    CreateUser createUser = new CreateUser(email, password, null);
    accessToken = createUserApi.createUser(createUser)
            .then().statusCode(403).assertThat()
            .body("success", equalTo(false))
            .body("message", equalTo("Email, password and name are required fields"))
            .extract().path("accessToken");
}
}