package creatingacouriertest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pens.CourierAPI;
import serialization.Courier;
import url.BaseURL;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreatingACourierTest {
    CourierAPI courierAPI = new CourierAPI();
    BaseURL baseURL = new BaseURL();

    @Before
    public void setUp() {
        baseURL.setUp();
    }

    @Test // Курьера можно создать; запрос возвращает правильный код ответа; успешный запрос возвращает ok: true
    @DisplayName("Creating a courier")
    @Description("Checking if a courier can be created and that the request returns the correct response code and correct response body")
    public void creatingACourier() {
        Courier courier = new Courier("Bus", "123", "Gleb");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier().then().assertThat().body("ok", equalTo(true)).
                and().statusCode(201);
    }

    @Test // Нельзя создать 2 одинаковых курьеров
    @DisplayName("Creating a courier twice")
    @Description("Checking that two identical couriers cannot be created")
    public void creatingACourierTwice() {
        Courier courier = new Courier("Bus", "123", "Gleb");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier();
        courierAPI.creatingACourier().then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).
                and().statusCode(409);

    }

    @Test
    // Чтобы создать курьера, нужно передать в ручку все обязательные поля - пароль; если одного из полей нет, запрос возвращает ошибку
    @DisplayName("Create a courier without a password")
    @Description("Trying to create a courier without a password")
    public void createACourierWithoutAPassword() {
        Courier courier = new Courier("Bus", "", "Gleb");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier().then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    // Чтобы создать курьера, нужно передать в ручку все обязательные поля - логин; если одного из полей нет, запрос возвращает ошибку
    @DisplayName("Create a courier without a login")
    @Description("Trying to create a courier without a login")
    public void createACourierWithoutALogin() {
        Courier courier = new Courier("", "123", "Gleb");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier().then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    // Чтобы создать курьера, нужно передать в ручку все обязательные поля - имя; если одного из полей нет, запрос возвращает ошибку
    @DisplayName("Create a courier without a name")
    @Description("Trying to create a courier without a name")
    public void createACourierWithoutAName() {
        Courier courier = new Courier("Bus", "123", "");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier().then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test // если создать пользователя с логином, который уже есть, возвращается ошибка
    @DisplayName("Creation a courier with a repeated login")
    @Description("Checking that creating a courier with a duplicate login is not possible")
    public void creationACourierWithARepeatedLogin() {
        Courier courier = new Courier("Bus", "1986", "Ivan");
        courierAPI.setCourier(courier);
        courierAPI.creatingACourier();
        Courier courier1 = new Courier("Bus", "1986", "Ivan");
        courierAPI.setCourier(courier1);
        courierAPI.creatingACourier().then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).
                and().statusCode(409);
    }
    @After
           public void deleteCourier(){
        courierAPI.deletingACourier();
    }
}
