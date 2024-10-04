package courierTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.courier.Courier;
import praktikum.courier.CourierChecks;
import praktikum.courier.CourierClient;
import praktikum.courier.CourierCredentials;

public class DeleteCourierTest {

    private CourierClient client = new CourierClient();
    private CourierChecks check = new CourierChecks();
    private Courier courier;
    int courierId;

    // Создаю курьера
    @Before
    public void setUp() {
        courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        // Авторизуюсь для получения ID курьера
        ValidatableResponse loginResponse = client.logIn(CourierCredentials.fromCourier(courier));
        courierId = check.checkLoggedIn(loginResponse);
    }

    @After
    public void tearDown() {
        // Выполняю удаление созданного курьера
        if (courierId > 0) {
            ValidatableResponse deleteResponse = client.delete(courierId);
            check.checkDeleted(deleteResponse);
        }
    }

    @Test
    @DisplayName("Successful courier deletion")
    public void successfulCourierDeletion() {
        // Удаляю курьера
        ValidatableResponse deleteResponse = client.delete(courierId);

        // Проверяю успешное удаление курьера, должно вернуться ok: true
        check.checkDeleted(deleteResponse);
        courierId = 0;
    }

    @Test
    @DisplayName("Deleting courier without ID fails")
    public void deleteCourierWithoutIdFails() {
        // Пытаюсь удалить курьера без указания обязательного поля ID
        ValidatableResponse deleteResponse = client.deleteWithoutId();

        // Проверяю, что если не указать ID, то вернется ошибка с кодом 400
        check.checkBadRequestForDeletion(deleteResponse);
    }

    @Test
    @DisplayName("Deleting non-existent courier fails")
    public void deleteNonExistentCourierFails() {
        // Пытаюсь удалить курьера с несуществующим ID
        ValidatableResponse deleteResponse = client.delete(999999); // ID, которого нет в системе

        // Проверяю, что попытка удалить курьера с несуществующим ID возвращает ошибку с кодом 404
        check.checkNotFoundForDeletion(deleteResponse);
    }
}