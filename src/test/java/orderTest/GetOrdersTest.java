package orderTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import praktikum.order.OrderClient;
import praktikum.order.OrderChecks;

public class GetOrdersTest {

    private OrderClient orderClient = new OrderClient();
    private OrderChecks check = new OrderChecks();

    @Test
    @DisplayName("Get orders list successfully")
    public void getOrdersListSuccessfully() {
        // Запрос на получение всех заказов
        ValidatableResponse response = orderClient.getOrders();

        // Проверяю что список заказов вернулся
        check.checkOrdersListReturned(response);
    }
}