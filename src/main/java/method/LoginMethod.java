package method;

import base.BaseClass;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.User;
import model.UserLogin;

import static io.restassured.RestAssured.given;

public class LoginMethod  extends BaseClass {

    @Step("Авторизация пользователя")
    public ValidatableResponse login(User user){
        return given()
                .spec(spec())
                .body(user).log().all()
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    @Step("Получение accessToken пользователя после авторизации.")
    public String returnUserAccessToken (User user) {
        return given()
                .spec(spec())
                .body(user)
                .when()
                .post(LOGIN_PATH)
                .then()
                .extract()
                .path("accessToken");

    }

    @Step("Изменение данных пользователя с авторизацией")
    public ValidatableResponse update(User user,String accessToken) {
        return given()
                .header(  "Authorization", "Bearer" + accessToken)
                .spec(spec())
                .body(user)
                .when()
                .patch(USER_UPDATE)
                .then()
                .log().all();

    }

    @Step("Изменение данных пользователя без авторизации")
    public ValidatableResponse updateWithoutToken(User user){
        return given()
                .spec(spec())
                .body(user)
                .log().all()
                .patch(LOGIN_PATH)
                .then();
    }
}
