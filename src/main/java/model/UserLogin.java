package model;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class UserLogin {

    private String email;
    private String password;

    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Step("Получение случайных данных для авторизации.")
    public static UserLogin getRandomUserLogin () {

        final String email = RandomStringUtils.randomAlphabetic(4) + "@" + RandomStringUtils.randomAlphabetic(4) + ".ru";
        final String password = RandomStringUtils.randomAlphabetic(10);

        return new UserLogin(email, password);
    }

    @Step("Получение логина и пароля из данных о регистрации.")
    public static UserLogin from(User user) {

        return new UserLogin(user.getEmail(), user.getPassword());

    }

    public UserLogin getCredentials(){
        return new UserLogin(email,password);
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
