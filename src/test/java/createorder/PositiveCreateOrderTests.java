package createorder;

import api.CreateOrderApi;
import api.CreateUserApi;
import api.DeleteUserApi;
import api.GetIngredientsApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateUser;
import model.GetIngredients;
import model.RandomData;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class PositiveCreateOrderTests {
    private static String accessToken;
    private static String randomEmail = RandomData.randomEmail();
    private static String randomPassword = RandomData.randomPassword(8);
    private static String randomName = RandomData.randomName();

    @BeforeClass
    public static void createUserForTest() {
        CreateUserApi createUserApi = new CreateUserApi();
        CreateUser createUser = new CreateUser(randomEmail, randomPassword, randomName);
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
        GetIngredientsApi getIngredientsApi = new GetIngredientsApi();
        List<String> ingredientIds = getIngredientsApi.getIngredients().then().statusCode(200).extract().jsonPath().getList("data._id");
        GetIngredients getIngredients = new GetIngredients();
        getIngredients.add(ingredientIds.get(0));
        getIngredients.add(ingredientIds.get(1));
        CreateOrderApi createOrderApi = new CreateOrderApi();
        createOrderApi.createOrder(getIngredients, accessToken).then().statusCode(200).assertThat()
                .body("success", equalTo(true))
                .body("order._id", notNullValue())
                .body("order.status", equalTo("done"))
                .body("order.owner.email", equalTo(randomEmail));

    }

    @Test
    @DisplayName("Проверка создания заказов без авторизации")
    @Description("Направление запроса на создание заказа без авторизации")
    public void positiveCreateOrderWithoutAuthTest() {
        GetIngredientsApi getIngredientsApi = new GetIngredientsApi();
        List<String> ingredientIds = getIngredientsApi.getIngredients().then().statusCode(200).extract().jsonPath().getList("data._id");
        GetIngredients getIngredients = new GetIngredients();
        getIngredients.add(ingredientIds.get(0));
        getIngredients.add(ingredientIds.get(1));
        CreateOrderApi createOrderApi = new CreateOrderApi();
        createOrderApi.createOrderWithoutAuth(getIngredients).then().statusCode(200).assertThat()
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }
}
