package generateData;

import io.qameta.allure.Step;
import model.User;
import net.datafaker.Faker;


public class UserFaker {

    @Step("Create random user data")
    public static User getRandomUserData() {
        Faker faker = new Faker();
        return new User(
                faker.internet().emailAddress(),
                faker.internet().password(),
                faker.name().firstName());
    }



}
