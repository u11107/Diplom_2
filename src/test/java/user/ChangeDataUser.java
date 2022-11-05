package user;

import baseClass.Base;
import generateData.DataGenerate;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ChangeDataUser extends Base {

    private String accessToken;

    @Before
    public void setUp() {
        user = DataGenerate.createUser();
        method.createUser(user);
        response = loginMethod.login(user);
        accessToken = response.log().all().extract().path("accessToken");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            method.deleteUser(accessToken).assertThat().statusCode(SC_ACCEPTED)
                    .body("success", is(true));
        }
    }

    @Test
    @DisplayName("Успешное изменение авторизованного пользователя.")
    public void userUpdateValidTest() {
        response = loginMethod.update(user,accessToken);
        response.assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Попытка изменени не авторизованного пользователя.")
    public void userUpdateNotValidTest() {
        user = DataGenerate.createUser();
        method.createUser(user);
        response = loginMethod.update(user,accessToken);
        response.assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", is(false));
    }


}
