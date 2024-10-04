package praktikum.courier;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierClient extends praktikum.Client {
    public static final String COURIER_PATH = "courier";

    @Step("Creating courier")
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Logging in courier")
    public ValidatableResponse logIn(CourierCredentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER_PATH + "/login")
                .then().log().all();
    }

    @Step("Deleting courier")
    public ValidatableResponse delete(int id) {
        return spec()
                //  .body(Map.of("id", id))
                .when()
                .delete(COURIER_PATH + "/" + id)
                .then().log().all();
    }

    @Step("Attempt to delete courier without ID")
    public ValidatableResponse deleteWithoutId() {
        return spec()
                .body("\"id\": ")
                .when()
                .delete(COURIER_PATH + "/")
                .then().log().all();
    }
}