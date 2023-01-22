package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CreatingUserTest {

        private final UserClient client = new UserClient();
        private final UserAssertations check = new UserAssertations();
        private final UserGenerator generator = new UserGenerator();
        private String accessToken = "null";

    @After
    public void deleteUser(){
        if (!accessToken.equals("null")) {
            ValidatableResponse deleteResponse = client.deleteUser(accessToken);
            check.deletedSuccessfully(deleteResponse);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUser(){
        User user = generator.random();
        ValidatableResponse response = client.createUser(user);
        accessToken = check.successfulResponse(response);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createUserTwice() {
        User user = generator.random();
        client.createUser(user);
        ValidatableResponse responseTwo = client.createUser(user);
        check.createdFailed(responseTwo);
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void createUserWithoutName() {
        User user = generator.random();
        user.setName("");
        ValidatableResponse response = client.createUser(user);
        check.createdWithoutOneFieldFailed(response);
    }

    @Test
    @DisplayName("Создание пользователя без эмейла")
    public void createUserWithoutEmail() {
        User user = generator.random();
        user.setEmail("");
        ValidatableResponse response = client.createUser(user);
        check.createdWithoutOneFieldFailed(response);
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    public void createUserWithoutPassword() {
        User user = generator.random();
        user.setPassword("");
        ValidatableResponse response = client.createUser(user);
        check.createdWithoutOneFieldFailed(response);
    }

}