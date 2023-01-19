package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserClient extends Client{

    public static final String ROOT = "/api/auth/";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(ROOT + "register")
                .then().log().all();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(ROOT + "login")
                .then().log().all();
    }

    @Step("Изменение данных авторизованного пользователя")
    public ValidatableResponse changeDataAuthorizedUser(User user, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(ROOT + "user")
                .then().log().all();
    }

    @Step("Изменение данных не авторизованного пользователя")
    public ValidatableResponse changeDataUnauthorizedUser(User user) {
        return spec()
                .body(user)
                .when()
                .patch(ROOT + "user")
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser (String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete(ROOT + "user")
                .then().log().all();
    }
}
