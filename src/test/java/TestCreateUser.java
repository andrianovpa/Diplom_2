import model.DeleteUser;
import user.CreateUserApi;
import model.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Test;
import user.DeleteUserApi;

import static java.util.function.Predicate.not;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;


public class TestCreateUser {
    private static String email = "andrianovpa1@gmail.com";
    private static String password = "123456781";
    private static String name = "Pavel1";
    private static String accessToken;

    @AfterClass
    public static void deleteUser() {
        DeleteUserApi deleteUserApi = new DeleteUserApi();
        DeleteUser deleteUser = new DeleteUser();
        deleteUserApi.deleteUser(accessToken);
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
//    @Test
//    @DisplayName("Негативная проверка создания курьера 1") // имя теста
//    @Description("Направляется запрос на создание курьера с уже зарегестрированными данными") // описание теста
//    public void negativeCreateCourierTest_1() {
//
//        CreateCourierApi createCourierApi = new CreateCourierApi();
//        CreateCourier courier = new CreateCourier(login, password, firstName);
//        createCourierApi.createCourier(courier).then().statusCode(409).assertThat().body("message", equalTo("Этот логин уже используется"));
//    }
//
//    @Test
//    @DisplayName("Негативная проверка создания курьера 2") // имя теста
//    @Description("Направляется запрос на создание курьера без поля login") // описание теста
//    public void negativeCreateCourierTest_2() {
//
//        CreateCourierApi createCourierApi = new CreateCourierApi();
//        CreateCourier courier = new CreateCourier(null, password, firstName);
//        createCourierApi.createCourier(courier).then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
//    }
//
//    @Test
//    @DisplayName("Негативная проверка создания курьера 3") // имя теста
//    @Description("Направляется запрос на создание курьера без поля password") // описание теста
//    public void negativeCreateCourierTest_3() {
//
//        CreateCourierApi createCourierApi = new CreateCourierApi();
//        CreateCourier courier = new CreateCourier(login, null, firstName);
//        createCourierApi.createCourier(courier).then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
//    }
//}
