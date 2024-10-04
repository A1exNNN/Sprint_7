package praktikum.courier;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CourierChecks {

    @Step("successful created")
    public void checkCreated(ValidatableResponse response) {
        boolean created = response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .path("ok");
        assertTrue(created);
    }

    @Step("Successful logged in")
    public int checkLoggedIn(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("id");
        assertNotEquals(0, id);
        return id;
    }

    @Step("Successful deleted")
    public void checkDeleted(ValidatableResponse response) {
        boolean deleted = response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("ok");
        assertTrue(deleted);
    }

    @Step("Check existing login conflict")
    public void checkLoginConflict(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_CONFLICT) // Должен быть код 409 конфликт
                .and()
                .body("message", equalTo("Этот логин уже используется")); // Автоматическое сравнение
    }

    @Step("Check create bad request (400)")
    public void checkBadRequestForCreation(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Check login error")
    public void checkLoginError(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_NOT_FOUND) // Должен вернутся код 404, в случае если логин или пароль неверный
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Check login bad request (400)")
    public void checkLoginBadRequest(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST) // Должен вернутся код 400
                .and()
                .body("message", equalTo("Недостаточно данных для входа")); // Проверка текста сообщения
    }

    @Step("Check deletion bad request (400)")
    public void checkBadRequestForDeletion(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)  // Должен вернутся код 400
                .and()
                .body("message", equalTo("Недостаточно данных для удаления курьера"));  // Проверка текста сообщения для удаления курьера
    }

    @Step("Check deletion not found (404)")
    public void checkNotFoundForDeletion(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)  // Должен вернутся код 404
                .and()
                .body("message", equalTo("Курьера с таким id нет"));  // Проверка текста сообщения при попытке удаления не существующего курьера
    }
}