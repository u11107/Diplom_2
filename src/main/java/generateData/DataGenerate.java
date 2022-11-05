package generateData;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.User;
import net.datafaker.Faker;
@Data
@AllArgsConstructor
public class DataGenerate {

    public static User createUser() {
        Faker faker = new Faker();
        var email = faker.internet().emailAddress();
        var password  =faker.password().toString();
        var name = faker.name().firstName();
        return new User(email, password, name);
    }



}
