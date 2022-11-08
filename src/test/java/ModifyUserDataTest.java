import generateData.UserFaker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import client.UserClient;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;


@DisplayName("Изменение данных пользователя")
public class ModifyUserDataTest extends BaseClass {

    @Before
    @DisplayName("создать пользователя и извлечь токен доступа")
    public void setUp() throws InterruptedException {
        User user = UserFaker.getRandomUserData();
        Response response = userClient.createUser(user);
        accessToken = response.path("accessToken");
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    @DisplayName("Изменить данные пользователя с авторизацией")
    public void modifyUserDataAuthorizedTest() {
        User newUser = UserFaker.getRandomUserData();
        Response responseChangedData = userClient.changeUserData(newUser, accessToken);
        responseChangedData
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(newUser.getEmail()))
                .body("user.name", equalTo(newUser.getName()));
    }

    @Test
    @DisplayName("Изменить данные пользователя без авторизацией")
    public void modifyUserDataWithoutAuthorizationTest() {
        User user = UserFaker.getRandomUserData();
        Response responseChangedData = userClient.changeUserData(user, "");
        responseChangedData
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @After
    @DisplayName("Удаление пользователя")
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }

}
