package method;

import base.BaseClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient{
    private final String ENDPOINT = "/api/auth";

    @Step("Create user")
    public Response createUser(User user) {
        return given()
                .spec(BaseClient.getBaseSpec())
                .and()
                .body(user)
                .when()
                .post(BASEURL + ENDPOINT + "/register");
    }

    @Step("Delete user")
    public Response deleteUser(String accessToken) {
        return given()
                .spec(BaseClient.getBaseSpec())
                .header("Authorization", accessToken)
                .delete(BASEURL + ENDPOINT + "/user");
    }


    @Step("User login")
    public Response loginUser(User user) {
        return given()
                .spec(BaseClient.getBaseSpec())
                .and()
                .body(user)
                .when()
                .post(BASEURL + ENDPOINT + "/login");
    }

    @Step("Change user data")
    public Response changeUserData(User user, String  accessToken) {
        return given()
                .spec(BaseClient.getBaseSpec())
                .header("Authorization", accessToken)
                .and()
                .body(user)
                .when()
                .patch(BASEURL + ENDPOINT + "/user");
    }
}
