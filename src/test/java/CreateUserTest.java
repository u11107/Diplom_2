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
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Создание пользователя")
public class CreateUserTest  extends BaseClass{

    @Before
    @DisplayName("СОздание случайного пользователя")
    public void setUp() throws InterruptedException {
        user = UserFaker.getRandomUserData();
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    @DisplayName("Создайте уникального пользователя с допустимыми полями")
    public void createUniqueUserTest(){
        Response response = userClient.createUser(user);
        accessToken = response.path("accessToken");
        response
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Создать дубликата пользовател")
    public void createDuplicateUserTest(){
        User duplicateUser = UserFaker.getRandomUserData();
        userClient.createUser(duplicateUser);
        Response response = userClient.createUser(duplicateUser);
        accessToken = response.path("accessToken");
        response
                .then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создать пользователя с отсутствующими данными")
    public void createUserMissingDataTest(){
        User user = new User();
        Response response = userClient.createUser(user);
        accessToken = response.path("accessToken");
        response
                .then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    @DisplayName("Удаление пользователя")
    public void tearDown(){
        if (accessToken != null) userClient.deleteUser(accessToken);
    }

}
