package user;

import baseClass.Base;
import generateData.DataGenerate;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class UserCreateTest extends Base {


    @Before
    public void setUp() {
        user = DataGenerate.createUser();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            method.deleteUser(accessToken).assertThat().statusCode(SC_ACCEPTED)
                    .body("success", is(true));
        }
    }


    @Test
    @DisplayName("Создание курьера с валидными данными")
    public void shouldCreatingCourierWithValidData() {
        response = method.createUser(user);
        response.assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true))
                .body("user.email", is(user.getEmail()))
                .body("user.name", is(user.getName()));
    }

    @Test
    @DisplayName("Создание курьера который уже зарегестрирован")
    // Тест падает с ошибкой 429
    public void checkCreateUserWithSystem() {
        method.createUser(user);
        response = method.createUser(user);
        response.assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .log().all()
                .body("success", is(false))
                .body("message", is("User already exists"));

    }

    @Test
    @DisplayName("Создание курьера который уже зарегестрирован без одного поля")
    public void checkCreateUserWithSystemfefe() {
        response = method.createUser(new User(user.getEmail(), user.getPassword()));
        response.assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .log().all()
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"));

    }
}
