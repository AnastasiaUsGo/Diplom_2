package order;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import user.User;
import user.UserAssertations;
import user.UserClient;
import user.UserGenerator;

public class ReceivingOrderTest {

    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertations check = new UserAssertations();
    private final OrderClient orderClient = new OrderClient();
    private final OrderAssertations orderCheck = new OrderAssertations();
    private final Faker faker = new Faker();

    @Test
    @DisplayName("Получение заказов авторизированным пользователем")

    public void getOrderWithAuthorization(){
        User user = generator.loginCredentials();
        ValidatableResponse userCreateResponse = client.loginUser(user);
        String accessToken = check.successfulResponse(userCreateResponse);
        ValidatableResponse response = orderClient.receivingUserOrders(accessToken);
        orderCheck.successfulReceiptListOrder(response);
    }

    @Test
    @DisplayName("Получение заказов неавторизованным пользователем")
    public void getOrderWithoutAuthorization(){
        String wrongAccessToken = (faker.random().hex(10));
        ValidatableResponse response = orderClient.receivingUserOrders(wrongAccessToken);
        orderCheck.responseUnauthorized(response);
    }
}
