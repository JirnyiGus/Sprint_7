package url;

import io.restassured.RestAssured;

public class BaseURL {
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
}
