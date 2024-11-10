package createorder;

import api.CreateOrderApi;
import api.CreateUserApi;
import api.DeleteUserApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CreateUser;
import model.GetIngredients;
import model.RandomData;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class NegativeCreateOrderTests {
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
    @DisplayName("Проверка создания заказов без авторизации")
    @Description("Направление запроса на создание заказа без авторизации")
    public void negativeCreateOrderWithoutIngredientsTest() {

        CreateOrderApi createOrderApi = new CreateOrderApi();
        createOrderApi.createOrderWithoutIndgredients(accessToken).then().statusCode(400).assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("Проверка создания заказов c неверным хешем ингредиентов")
    @Description("Направление запроса на создание заказа c неверным хешем ингредиентов")
    public void negativeCreateOrderInvalidHashIngredientsTest() {
        GetIngredients getIngredients = new GetIngredients();
        getIngredients.add(RandomData.randomHash(30));
        getIngredients.add(RandomData.randomHash(30));
        CreateOrderApi createOrderApi = new CreateOrderApi();
        createOrderApi.createOrder(getIngredients, accessToken).then().statusCode(500);
    }
}
