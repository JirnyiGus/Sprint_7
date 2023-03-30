package pens;

import io.restassured.response.Response;
import serialization.Order;

import static io.restassured.RestAssured.given;

public class OrderAPI {
    Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    public Response createOrder() {
        Response response = given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
        return response;
    }

    public Response getListOrder() {
        Response response = given().get("/api/v1/orders");
        return response;
    }

}
