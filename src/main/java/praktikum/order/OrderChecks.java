package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;

public class OrderChecks {

    @Step("Order creation successful check")
    public int checkOrderCreated(ValidatableResponse response) {
        response
                .assertThat() // Проверяю создания заказа
                .statusCode(HTTP_CREATED)
                .and()
                .body("track", notNullValue()); // Проверяю что поле "track" не null
        return response.extract().path("track");
    }

    @Step("Orders list is returned successfully check")
    public void checkOrdersListReturned(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_OK)  // Проверяю успешен ли запрос
                .and()
                .body("orders", not(empty()));  // Проверяю что список заказов не пустой
    }

    @Step("Order accepted successfully check")
    public void checkOrderAccepted(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_OK)  // Должен быть ответ код 200
                .body("ok", equalTo(true));  // Проверка, что поле "ok" правда
    }

    @Step("Bad request for accept order (400) check")
    public void checkBadRequestForAccept(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)  // Должен быть ответ код 400
                .body("message", equalTo("Недостаточно данных для поиска"));  // Проверка текста ошибки
    }

    @Step("Courier not found (404) check")
    public void checkNotFoundCourier(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)  // Должен быть ответ код 404
                .body("message", equalTo("Курьера с таким id не существует"));  // Проверка текста ошибки
    }

    @Step("Order not found (404) check")
    public void checkNotFoundOrder(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)  // Должен быть ответ код 404
                .body("message", equalTo("Заказа с таким id не существует"));  // Проверка текста ошибки
    }

    @Step("Order is returned successfully check")
    public void checkOrderReturned(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_OK)  // Должен быть ответ код 200
                .body("order", notNullValue());  // Проверяю что объект заказа не null
    }

    @Step("Order not found for incorrect track number check")
    public void checkOrderNotFoundForIncorrectTrack(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)  // Должен быть ответ код 404
                .body("message", equalTo("Заказ не найден"));  // Проверка текста ошибки
    }

}