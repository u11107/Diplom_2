package user;

import baseClass.Base;
import generateData.DataGenerate;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;

public class UserLoginTest extends Base {

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
    @DisplayName("Логин пользователя с некорректными данными")
    public void loginWrongCredsUserTest() {
        response = loginMethod.login(user);
        accessToken = response.log().all()
                .extract().path("accessToken");
        assertNull(accessToken);
        int statusCode = response.log().all().extract().statusCode();
        boolean responseOk = response.log().all().extract().path("success");
        assertThat("401", statusCode, is(SC_UNAUTHORIZED));
        assertThat("success",responseOk, is(false));

    }


    @Test
    @DisplayName("Логин пользователя")
    public void loginValidUserTest() {
        method.createUser(user);
        response = loginMethod.login(user);
        accessToken = response.log().all().extract().path("accessToken");
        int statusCode = response.log().all().extract().statusCode();
        boolean responseOk = response.log().all().extract().path("success");
        assertThat("200", statusCode, is(SC_OK));
        assertThat("accessToken",accessToken, is(accessToken));
        assertThat("success",responseOk, is(true));

    }





}
