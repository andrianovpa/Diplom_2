package createorder;

import api.CreateOrderApi;
import api.CreateUserApi;
import api.DeleteUserApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateOrder;
import model.CreateUser;
import model.GetIngredients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class PositiveCreateOrderTests {
    private static String accessToken;
    private static String email = "andrianovpa@gmail.com";
    private static String password = "12345678";
    private static String name = "Pavel";

    @BeforeClass
    public static void createUserForTest() {
        CreateUserApi createUserApi = new CreateUserApi();
        CreateUser createUser = new CreateUser(email, password, name);
        accessToken = createUserApi.createUser(createUser).then().extract().path("accessToken");
    }

    @AfterClass
    public static void deleteUser() {


        DeleteUserApi deleteUserApi = new DeleteUserApi();
        deleteUserApi.deleteUser(accessToken);

    }

    @Test
    @DisplayName("Проверка создания заказа с авторизацией")
    @Description("Направление запроса на создание заказа с авторизацией пользователя")
    public void positiveCreateOrderTest() {
        GetIngredients getIngredients = new GetIngredients();
        getIngredients.add("61c0c5a71d1f82001bdaaa70");
        getIngredients.add("61c0c5a71d1f82001bdaaa72");
        CreateOrderApi createOrderApi = new CreateOrderApi();
        createOrderApi.createOrder(getIngredients, accessToken).then().statusCode(200).assertThat()
                .body("success", equalTo(true))
                .body("order._id", notNullValue())
                .body("order.status", equalTo("done"))
                .body("order.owner.email", equalTo(email));

    }

    @Test
    @DisplayName("Проверка создания заказов без авторизации")
    @Description("Направление запроса на создание заказа без авторизации")
    public void positiveCreateOrderWithoutAuthTest() {
        GetIngredients getIngredients = new GetIngredients();
        getIngredients.add("61c0c5a71d1f82001bdaaa70");
        getIngredients.add("61c0c5a71d1f82001bdaaa72");
        CreateOrderApi createOrderApi = new CreateOrderApi();
        createOrderApi.createOrderWithoutAuth(getIngredients).then().statusCode(200).assertThat()
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }
}
