package method;

import base.BaseClass;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.User;

import static io.restassured.RestAssured.given;

public class UserMethod  extends BaseClass {

    @Step("Создание курьера с параметрами")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(spec())
                .body(user)
                .when()
                .post(USER_PATH)
                .then()
                .log().all();
    }


    @Step("Удаление пользователя")
    //create
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(spec())
                .delete(USER_DELETE)
                .then();
    }
}
