package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class LoginUserTest {

    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertations check = new UserAssertations();
    private String accessToken = "null";

    @After
    public void deleteUser() {
        if (!accessToken.equals("null")) {
            ValidatableResponse deleteResponse = client.deleteUser(accessToken);
            check.deletedSuccessfully(deleteResponse);
        }
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void loginUser(){
        User user = generator.loginCredentials();
        ValidatableResponse response = client.loginUser(user);
        check.successfulResponse(response);
    }

    @Test
    @DisplayName("Логин с неверным email")
    public void wrongEmailLoginUser(){
        User user = generator.loginCredentials();
        user.setEmail("apodroid@ya.ru");
        ValidatableResponse response = client.loginUser(user);
        check.loginFailed(response);
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void wrongPasswordLoginUser() {
        User user = generator.loginCredentials();
        user.setPassword("12345678");
        ValidatableResponse response = client.loginUser(user);
        check.loginFailed(response);
    }
}
