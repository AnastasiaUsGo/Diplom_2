package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderAssertations {

    @Step("Успешное создание заказа")
    public void successfulOrderCreation(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));
    }

    @Step("При создании заказа не передан ни один ингредиент")
    public void ingredientMustBeProvided(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("При создании заказа передан невалидный хеш ингредиента")
    public void invalidIngredientHash(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Step("Ответ на получения заказа без авторизация")
    public void responseWithoutAuthorization(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Ответ об успешном получении списка заказов")
    public void successfulReceiptListOrder(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());
    }
}
