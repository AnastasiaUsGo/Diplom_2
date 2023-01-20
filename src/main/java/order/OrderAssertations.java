package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderAssertations {

    @Step("В запросе не передан ни один ингредиент")
    public void ingredientMustBeProvided(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Успешное создание заказа")
    public void successfulOrderCreation(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Step("В запросе передан невалидный хеш ингредиента")
    public void invalidIngredientHash(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Step("Запрос без авторизация (Код 401)")
    public void responseUnauthorized(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Ответ об успешном получении списка заказо")
    public void successfulReceiptListOrder(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());
    }
}
