package loginacouriertest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pens.CourierAPI;
import serialization.Courier;
import url.BaseURL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginACourierTest {
    CourierAPI courierAPI = new CourierAPI();
    BaseURL baseURL = new BaseURL();

    @Before
    public void setUp() {
        baseURL.setUp();
    }

    @Test // курьер может авторизоваться; успешный запрос возвращает id
    @DisplayName("Login a courier")
    @Description("Checking that the curter can log in and a successful request returns an id")
    public void loginACourier() {
        Courier courier = new Courier("Gus", "123", "Gleb");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier();
        Courier courier1 = new Courier("Gus", "123");
        courierAPI.setCourier(courier1);
        courierAPI.loginCourier().then().assertThat().body("id", notNullValue()).
        and().statusCode(200);

    }

    @Test // для авторизации нужно передать все обязательные поля; если какого-то поля нет, запрос возвращает ошибку
    @DisplayName("Login a courier without a password")
    @Description("Checking that all required fields must be passed for authorization, and if some field is missing, the request will return an error")
    public void loginACourierWithoutAPassword() {
        Courier courier = new Courier("Bus", "123", "Gleb");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier();
        Courier courier1 = new Courier("Bus", "");
        courierAPI.setCourier(courier1);
        courierAPI.loginCourier().then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test // для авторизации нужно передать все обязательные поля; если какого-то поля нет, запрос возвращает ошибку
    @DisplayName("Login a courier without a login")
    @Description("Checking that all required fields must be passed for authorization, and if some field is missing, the request will return an error")
    public void loginACourierWithoutALogin() {
        Courier courier = new Courier("Bus", "123", "Gleb");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier();
        Courier courier1 = new Courier("", "123");
        courierAPI.setCourier(courier1);
        courierAPI.loginCourier().then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test // система вернёт ошибку, если неправильно указать логин или пароль;
    @DisplayName("Login with wrong login")
    @Description("Checking that the system will return an error if the username or password is incorrect")
    public void loginWithWrongLogin() {
        Courier courier = new Courier("Bus", "123", "Gleb");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier();
        Courier courier1 = new Courier("Tost", "123");
        courierAPI.setCourier(courier1);
        courierAPI.loginCourier().then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test // система вернёт ошибку, если неправильно указать логин или пароль;
    @DisplayName("Login with wrong password")
    @Description("Checking that the system will return an error if the username or password is incorrect")
    public void loginWithWrongPassword() {
        Courier courier = new Courier("Bus", "123", "Gleb");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier();
        Courier courier1 = new Courier("Bus", "123678");
        courierAPI.setCourier(courier1);
        courierAPI.loginCourier().then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test // если авторизоваться под несуществующим пользователем, запрос возвращает ошибку
    @DisplayName("Authorization with missing data")
    @Description("Checking that if you log in as a non-existent user, the request returns an error")
    public void authorizationWithMissingData() {
        Courier courier = new Courier("Bus", "123", "Gleb");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier();
        Courier courier1 = new Courier("ytka", "123643");
        courierAPI.setCourier(courier1);
        courierAPI.loginCourier().then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
    @After
    public void deleteCourier(){
        courierAPI.deletingACourier();
    }
}
