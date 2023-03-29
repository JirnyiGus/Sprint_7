package pens;

import io.restassured.response.Response;
import serialization.Courier;

import static io.restassured.RestAssured.given;

public class CourierAPI {

    Courier courier;

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    //Ручка для логина курьера
    public Response loginCourier() {

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

    // Ручка для создания курьера
    public Response creatingACourier() {

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    //Ручка для запроса id
    public Response getId() {
        Response response = given()
                .get("/api/v1/courier") // отправка GET-запроса
                .then().extract().body().path("id");
        return response;
    }

    //Ручка для удаления курьера
    public void deletingACourier() {
        Integer id = given().header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then().extract().body().path("id");
        if (id != null) {
            given().delete("/api/v1/courier/" + id);
        }
    }

}
