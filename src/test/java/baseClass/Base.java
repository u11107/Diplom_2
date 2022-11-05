package baseClass;

import io.restassured.response.ValidatableResponse;
import method.LoginMethod;
import method.UserMethod;
import model.User;
import model.UserResponse;

public class Base{

    protected ValidatableResponse response;
    protected User user;
    protected UserMethod method = new UserMethod();

    protected String accessToken;

    protected LoginMethod loginMethod = new LoginMethod();

    protected UserResponse userResponse;

}