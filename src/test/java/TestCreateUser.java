import user.CreateUserApi;
import model.CreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;


public class TestCreateCourier {
    private static String login = "Pavel_Sprint_7";
    private static String password = "12345678";
    private static String firstName = "Павел";

    @AfterClass
    public static void deleteCourier() {
        LoginCourierApi loginCourierApi = new LoginCourierApi();
        LoginCourier loginCourier = new LoginCourier(login, password);
        int id = loginCourierApi.loginCourier(loginCourier).then().extract().path("id");

        DeleteCourierApi deleteCourierApi = new DeleteCourierApi();
        DeleteCourier deleteCourier = new DeleteCourier(id);
        deleteCourierApi.deleteCourier(deleteCourier, id);

    }

    @Test
    @DisplayName("Положительная проверка создания курьера") // имя теста
    @Description("Направялется запрос на создание курьера с валидными данными") // описание теста
    public void positiveCreateCourierTest() {

        CreateCourierApi createCourierApi = new CreateCourierApi();
        CreateCourier courier = new CreateCourier(login, password, firstName);
        createCourierApi.createCourier(courier).then().statusCode(201).assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Негативная проверка создания курьера 1") // имя теста
    @Description("Направляется запрос на создание курьера с уже зарегестрированными данными") // описание теста
    public void negativeCreateCourierTest_1() {

        CreateCourierApi createCourierApi = new CreateCourierApi();
        CreateCourier courier = new CreateCourier(login, password, firstName);
        createCourierApi.createCourier(courier).then().statusCode(409).assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Негативная проверка создания курьера 2") // имя теста
    @Description("Направляется запрос на создание курьера без поля login") // описание теста
    public void negativeCreateCourierTest_2() {

        CreateCourierApi createCourierApi = new CreateCourierApi();
        CreateCourier courier = new CreateCourier(null, password, firstName);
        createCourierApi.createCourier(courier).then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Негативная проверка создания курьера 3") // имя теста
    @Description("Направляется запрос на создание курьера без поля password") // описание теста
    public void negativeCreateCourierTest_3() {

        CreateCourierApi createCourierApi = new CreateCourierApi();
        CreateCourier courier = new CreateCourier(login, null, firstName);
        createCourierApi.createCourier(courier).then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}