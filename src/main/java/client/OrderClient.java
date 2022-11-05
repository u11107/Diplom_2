package method;

import base.BaseClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private final String ENDPOINT = "/api/orders";

    @Step("Get ingredients id")
    public ArrayList<String> getIngredientsId() {
        return given()
                .spec(BaseClient.getBaseSpec())
                .get(BASEURL + "/api/ingredients")
                .path("data._id");
    }

    @Step("Create order")
    public Response createOrder(Order data, String accessToken) {
        return given()
                .spec(BaseClient.getBaseSpec())
                .header("Authorization", accessToken)
                .and()
                .body(data)
                .when()
                .post(BASEURL + ENDPOINT);
    }

    @Step("Get user's orders")
    public Response getUserOrders(String accessToken) {
        return given()
                .spec(BaseClient.getBaseSpec())
                .header("Authorization", accessToken)
                .get(BASEURL + ENDPOINT);
    }
}