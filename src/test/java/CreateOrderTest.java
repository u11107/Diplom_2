import generateData.UserFaker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;

@DisplayName("Создание заказа")
public class CreateOrderTest extends BaseClass {



    @Before
    @DisplayName("Создайте пользователя и извлеките токен доступа")
    public void setUp() {
        orderIngredientsIdList = new Order(orderClient.getIngredientsId());
        user = UserFaker.getRandomUserData();
    }

    @Test
    @DisplayName("Создать заказ без авторизации")
    public void createOrderWithoutAuthorizationTest(){
        Response response = orderClient.createOrder(orderIngredientsIdList,"");
        response
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("name", notNullValue());
    }

    @Test
    @DisplayName("Создать заказ с авторизацией")
//    тест падает с ошибкой 429
    public void createOrderWithAuthorizationTest(){
        Response userCreationResponse = userClient.createUser(user);
        accessToken = userCreationResponse.path("accessToken");

        Response createOrderResponse = orderClient.createOrder(orderIngredientsIdList, accessToken);
        createOrderResponse
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("name", notNullValue());
    }

    @Test
    @DisplayName("Создать заказ без ингредиентов")
    public void createOrderNoIngredientsTest(){
        Response response = orderClient.createOrder(new Order(), "");
        response
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создать заказ с недопустимым идентификатором ингредиент")
    public void createOrderWithInvalidIngredientId(){
        ArrayList<String> listInvalidId = new ArrayList<>();
        listInvalidId.add(invalidOrderId);

        Response response = orderClient.createOrder(new Order(listInvalidId),"");
        response
                .then()
                .statusCode(500)
                .body("$", hasItem("Internal Server Error"));
    }

    @After
    @DisplayName("Удаление пользрователя")
    public void tearDown() {
        if (accessToken != null) userClient.deleteUser(accessToken);
    }
}

