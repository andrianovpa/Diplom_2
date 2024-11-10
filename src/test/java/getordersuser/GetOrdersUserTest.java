package model;

import api.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersUserTest {
    private static String accessToken;
    private static String email = "andrianovpa@gmail.com";
    private static String password = "12345678";
    private static String name = "Pavel";

    @BeforeClass
    public static void createUserForTest() {
        CreateUserApi createUserApi = new CreateUserApi();
        CreateUser createUser = new CreateUser(email, password, name);
        accessToken = createUserApi.createUser(createUser).then().extract().path("accessToken");
        GetIngredientsApi getIngredientsApi = new GetIngredientsApi();
        List<String> ingredientIds = getIngredientsApi.getIngredients().then().statusCode(200).extract().jsonPath().getList("data._id");
        GetIngredients getIngredients = new GetIngredients();
        getIngredients.add(ingredientIds.get(0));
        getIngredients.add(ingredientIds.get(2));
        CreateOrderApi createOrderApi = new CreateOrderApi();
        createOrderApi.createOrder(getIngredients, accessToken);
    }

    @AfterClass
    public static void deleteUser() {


        DeleteUserApi deleteUserApi = new DeleteUserApi();
        deleteUserApi.deleteUser(accessToken);

    }
    @Test
    @DisplayName("Проверка получния заказов пользователя")
    @Description("Направляется запрос на получение заказов пользователя")
    public void getOrdersUserWithAuthTest() {
        GetOrdersUserApi getOrdersUserApi = new GetOrdersUserApi();
        getOrdersUserApi.getOrdersUser(accessToken).then().statusCode(200).assertThat()
                .body("success", equalTo(true))
                .body("orders", notNullValue())
                .body("total", notNullValue())
                .body("totalToday", notNullValue());
    }
    @Test
    @DisplayName("Проверка получния заказов пользователя без авторизации")
    @Description("Направляется запрос на получение заказов пользователя без авторизационного токена")
    public void getOrdersUserWithoutAuthTest() {
        GetOrdersUserApi getOrdersUserApi = new GetOrdersUserApi();
        getOrdersUserApi.getOrdersUser(null).then().statusCode(401).assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
