package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import user.Client;

public class OrderClient extends Client {
    public static final String ROOT = "/api/orders";

    @Step("Создание заказа авторизованным пользователем")
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Получение заказа авторизованным пользователем")
    public ValidatableResponse receivingUserOrders(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get(ROOT)
                .then().log().all();
    }

    @Step("Создание заказа неавторизованным пользователем")
    public ValidatableResponse creatingOrderUnauthorizedUser(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Получение заказа авторизованным пользователем")
    public ValidatableResponse getOrdersWithoutAuthorization() {
        return spec()
                .when()
                .get(ROOT)
                .then().log().all();
    }
}
