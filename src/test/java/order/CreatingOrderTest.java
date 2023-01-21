package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserAssertations;
import user.UserClient;
import user.UserGenerator;

public class CreatingOrderTest {

    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertations check = new UserAssertations();
    private final OrderClient orderClient = new OrderClient();
    private final OrderAssertations orderCheck = new OrderAssertations();
    private String accessToken = "null";

    @Before
    public void createUser() {
        User user = generator.loginCredentials();
        ValidatableResponse response = client.loginUser(user);
        accessToken = check.successfulResponse(response);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией c ингредиентами")
    public void creatingOrderWithAuthorizationWithIngredients() {
        Order order = Order.getOrder();
        ValidatableResponse createResponse = orderClient.createOrder(order, accessToken);
        orderCheck.successfulOrderCreation(createResponse);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и с неверным хешем ингредиентов")
    public void creatingOrderWithAuthorizationWithInvalidIngredients() {
        Order order = Order.getIncorrectOrder();
        ValidatableResponse createResponse = orderClient.createOrder(order, accessToken);
        orderCheck.invalidIngredientHash(createResponse);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией без ингредиентов")
    public void creatingOrderWithAuthorizationWithoutIngredients() {
        Order order = new Order(null);
        ValidatableResponse createResponse = orderClient.createOrder(order, accessToken);
        orderCheck.ingredientMustBeProvided(createResponse);
    }

    @Test
    @DisplayName("Создание заказа без авторизации c ингредиентами")
    public void creatingOrderWithoutAuthorization() {
        Order order = Order.getOrder();
        ValidatableResponse createResponse = orderClient.creatingOrderWithoutAuthorization(order);
        orderCheck.successfulOrderCreation(createResponse);
    }

}






