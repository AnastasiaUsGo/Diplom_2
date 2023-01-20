package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserAssertations {

    @Step("Успешный ответ")
    public String SuccessfulResponse(ValidatableResponse response){
        return response.assertThat()
                .body("success", equalTo(true))
                .statusCode(HTTP_OK)
                .extract()
                .path("accessToken");
    }

    @Step("Ответ при создании пользователя, который уже зарегистрирован")
    public void createdFailed(ValidatableResponse response){
        response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Step("Ответ при создании пользователя, если не заполнить одно из обязательных полей")
    public void createdWithoutOneFieldFailed(ValidatableResponse response){
        response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Ответ, если логин или пароль неверные или нет одного из полей при вхождении на сайт")
    public void loginFailed(ValidatableResponse response){
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Ответ,если не выполнена авторизация")
    public void unauthorizedFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Ответ на успешное удаление пользователя")
    public void deletedSuccessfully(ValidatableResponse response){
        response.assertThat()
                .statusCode(HTTP_ACCEPTED)
                .body("message", equalTo("User successfully removed"));
    }
}
