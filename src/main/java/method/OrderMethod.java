package method;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Order;

import static io.restassured.RestAssured.given;

public class OrderMethod {

    @Step("Отправка POST запроса на api/orders")
    public static Response sendPostRequestOrders(String accessToken, Order ingredients) {
        return given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(ingredients)
                .post("/api/orders");
    }

    @Step("Отправка GET запроса на api/orders")
    public static Response sendGetRequestOrders(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .get("/api/orders");
    }

    @Step("Создание объекта заказ")
    public static Order createObjectOrder(String[] ingredients) {
        return new Order(ingredients);
    }

    @Step("Проверка соответствия кода ответа при создании заказа с невалидным хэшем")
    public static void checkStatusCodeWithInvalidHash(Response response, int statusCode) {
        response.then()
                .assertThat()
                .statusCode(statusCode);
    }
}
