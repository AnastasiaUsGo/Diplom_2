package user;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class ChangingUserDataTest {
    private final UserClient client = new UserClient();
    private final UserAssertations check = new UserAssertations();
    private final UserGenerator generator = new UserGenerator();
    private final Faker faker = new Faker();
    private String accessToken = "null";

    @After
    public void deleteUser(){
        if (!accessToken.equals("null")) {
            ValidatableResponse deleteResponse = client.deleteUser(accessToken);
            check.deletedSuccessfully(deleteResponse);
        }
    }

    @Test
    @DisplayName("Изменение имени пользователя с авторизацией")
    public void changeName(){
        User user = generator.random();
        ValidatableResponse response = client.createUser(user);
        accessToken = check.successfulResponse(response);
        user.setName(faker.name().firstName());
        ValidatableResponse changeResponse = client.changeDataAuthorizedUser(user, accessToken);
    }

    @Test
    @DisplayName("Изменение эмейла пользователя с авторизацией")
    public void changeEmail(){
        User user = generator.random();
        ValidatableResponse response = client.createUser(user);
        accessToken = check.successfulResponse(response);
        user.setEmail(faker.internet().emailAddress());
        ValidatableResponse changeResponse = client.changeDataAuthorizedUser(user, accessToken);
    }

    @Test
    @DisplayName("Изменение пароля пользователя с авторизацией")
    public void changePassword(){
        User user = generator.random();
        ValidatableResponse response = client.createUser(user);
        accessToken =  check.successfulResponse(response);
        user.setPassword(faker.internet().password(6,10));
        ValidatableResponse changeResponse = client.changeDataAuthorizedUser(user, accessToken);
    }

    @Test
    @DisplayName("Изменение имени пользователя без авторизации")
    public void changeNameWithoutAuthorization(){
        User user = generator.random();
        user.setName(faker.name().firstName());
        String wrongAccessToken = (faker.random().hex(11));
        ValidatableResponse changeResponse = client.changeDataUnauthorizedUser(user, wrongAccessToken);
        check.unauthorizedFailed(changeResponse);
    }

    @Test
    @DisplayName("Изменение эмейла пользователя без авторизации")
    public void changeEmailWithoutAuthorization(){
        User user = generator.random();
        user.setEmail(faker.internet().emailAddress());
        String wrongAccessToken = (faker.random().hex(11));
        ValidatableResponse changeResponse = client.changeDataUnauthorizedUser(user, wrongAccessToken);
        check.unauthorizedFailed(changeResponse);
    }

    @Test
    @DisplayName("Изменение пароля пользователя без авторизации")
    public void changePasswordWithoutAuthorization(){
        User user = generator.random();
        user.setPassword(faker.internet().password(6, 10));
        String wrongAccessToken = (faker.random().hex(11));
        ValidatableResponse changeResponse = client.changeDataUnauthorizedUser(user, wrongAccessToken);
        check.unauthorizedFailed(changeResponse);
    }
}
