import generateData.UserFaker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import client.OrderClient;
import client.UserClient;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Получение заказа")
public class GetUserOrderTest extends BaseClass{

    @Before
    public void setUp() throws InterruptedException {
        User user = UserFaker.getRandomUserData();
        Response response = userClient.createUser(user);
        accessToken = response.path("accessToken");
        TimeUnit.SECONDS.sleep(1);

    }

    @Test
    @DisplayName("Получить заказы пользователя с авторизацией")
    public void getUserOrderdsWithAuthorizationTest(){
        Response response = orderClient.getUserOrders(accessToken);
        response
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("orders", notNullValue());
    }

    @Test
    @DisplayName("Получить заказы пользователя без авторизации")
    public void getUserOrderdsWithoutAuthorizationTest(){
        Response response = orderClient.getUserOrders("");
        response
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @After
    @DisplayName("Удаление  пользователя")
    public void tearDown() {
        if (accessToken != null) userClient.deleteUser(accessToken);
    }
}
