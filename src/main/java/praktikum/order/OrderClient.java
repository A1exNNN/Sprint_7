package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;

public class OrderClient extends Client {
    public static final String ORDER_PATH = "orders";

    @Step("Creating order")
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    @Step("Get all orders")
    public ValidatableResponse getOrders() {
        return spec()
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }

    @Step("Accept order")
    public ValidatableResponse acceptOrder(int orderId, int courierId) {
        return spec()
                .queryParam("courierId", courierId)
                .when()
                .put(ORDER_PATH + "/accept/" + orderId)
                .then().log().all();
    }

    @Step("Accept order without courier ID")
    public ValidatableResponse acceptOrderWithoutCourierId(int orderId) {
        return spec()
                .when()
                .put(ORDER_PATH + "/accept/" + orderId)
                .then().log().all();
    }

    @Step("Accept order without order ID")
    public ValidatableResponse acceptOrderWithoutOrderId(int courierId) {
        return spec()
                .queryParam("courierId", courierId)
                .when()
                .put(ORDER_PATH + "/accept/")
                .then().log().all();
    }

    @Step("Get order by track")
    public ValidatableResponse getOrderByTrack(int trackNumber) {
        return spec()
                .queryParam("t", trackNumber)
                .when()
                .get(ORDER_PATH + "/track")
                .then().log().all();
    }

    @Step("Get order without track number")
    public ValidatableResponse getOrderWithoutTrack() {
        return spec()
                .when()
                .get(ORDER_PATH + "/track")
                .then().log().all();
    }
}