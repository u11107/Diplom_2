package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseClass {

    private final static String BASE_URL = "https://stellarburgers.nomoreparties.site";
    protected final static String USER_PATH = "/api/auth/register";
    protected final static String LOGIN_PATH = "/api/auth/login";
    protected final static String USER_DELETE = "/api/auth/user";

    protected  final static String USER_UPDATE = "/api/auth/user";







    public RequestSpecification spec(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}
