package orderTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.courier.Courier;
import praktikum.courier.CourierChecks;
import praktikum.courier.CourierClient;
import praktikum.courier.CourierCredentials;
import praktikum.order.Order;
import praktikum.order.OrderChecks;
import praktikum.order.OrderClient;

public class GetOrderByTrackTest {

    private CourierClient courierClient = new CourierClient();
    private OrderClient orderClient = new OrderClient();
    private CourierChecks courierCheck = new CourierChecks();
    private OrderChecks orderCheck = new OrderChecks();

    private int courierId;
    private int orderId;
    private int trackNumber;

    private Courier courier;

    // Создаю курьера
    @Before
    public void setUp() {
        courier = Courier.random();
        ValidatableResponse createCourierResponse = courierClient.createCourier(courier);
        courierCheck.checkCreated(createCourierResponse);

        ValidatableResponse loginResponse = courierClient.logIn(CourierCredentials.fromCourier(courier));
        courierId = courierCheck.checkLoggedIn(loginResponse);

        Order order = new Order(
                "Sasha",
                "Chernov",
                "Kaluga, Groove str.",
                "7",
                "+7 777 777 77 77",
                6,
                "2024-10-06",
                "don't worry, be happy",
                new String[]{"BLACK"}
        );
        ValidatableResponse createOrderResponse = orderClient.createOrder(order);
        orderId = orderCheck.checkOrderCreated(createOrderResponse);
        trackNumber = createOrderResponse.extract().path("track");
    }

    // Удаление курьера после тестов
    @After
    public void tearDown() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Successfully get order by track number")
    public void getOrderByTrackSuccessfully() {
        ValidatableResponse getOrderResponse = orderClient.getOrderByTrack(trackNumber);
        orderCheck.checkOrderReturned(getOrderResponse);
    }

    @Test
    @DisplayName("Get order fails without track number")
    public void getOrderFailsWithoutTrackNumber() {
        ValidatableResponse getOrderResponse = orderClient.getOrderWithoutTrack();
        orderCheck.checkBadRequestForAccept(getOrderResponse);
    }

    @Test
    @DisplayName("Get order fails with non-existent track number")
    public void getOrderFailsWithNonExistentTrackNumber() {
        ValidatableResponse getOrderResponse = orderClient.getOrderByTrack(777777);
        orderCheck.checkOrderNotFoundForIncorrectTrack(getOrderResponse);
    }
}