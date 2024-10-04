package orderTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.order.Order;
import praktikum.order.OrderClient;
import praktikum.order.OrderChecks;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private OrderClient orderClient = new OrderClient();
    private OrderChecks check = new OrderChecks();

    private String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Test with color: {0}")
    public static Collection<Object[]> getColorData() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        });
    }

    @Test
    @DisplayName("Create order with different color options")
    public void createOrderWithColor() {
        Order order = new Order(
                "Sasha",
                "Chernov",
                "Kaluga, Groove str.",
                "7",
                "+7 777 777 77 77",
                7,
                "2024-10-06",
                "don't worry, be happy",
                color
        );

        ValidatableResponse response = orderClient.createOrder(order);
        check.checkOrderCreated(response);  // Проверяю создание заказа
    }
}