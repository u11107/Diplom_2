package client;

import base.BaseClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {

    @Step("Получение id ингридиентов")
    public ArrayList<String> getIngredientsId() {
        return given()
                .spec(BaseClient.getBaseSpec())
                .get(BASEURL + "/api/ingredients")
                .path("data._id");
    }

    @Step("Создание заказа")
    public Response createOrder(Order data, String accessToken) {
        return given()
                .spec(BaseClient.getBaseSpec())
                .header("Authorization", accessToken)
                .and()
                .body(data)
                .when()
                .post(BASEURL + ENDPOINT);
    }

    @Step("Получение заказов пользователя")
    public Response getUserOrders(String accessToken) {
        return given()
                .spec(BaseClient.getBaseSpec())
                .header("Authorization", accessToken)
                .get(BASEURL + ENDPOINT);
    }
}