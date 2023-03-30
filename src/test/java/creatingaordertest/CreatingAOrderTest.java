package creatingaordertest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pens.OrderAPI;
import serialization.Order;
import url.BaseURL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreatingAOrderTest {
    @Before
    public void setUp() {
        baseURL.setUp();
    }

    OrderAPI orderAPI = new OrderAPI();
    BaseURL baseURL = new BaseURL();
    private  Order order;


    public CreatingAOrderTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] test() {
        return new Object[][]{
                {new Order("Глеб", "Петров", "Москва флотская 3", "4", "89035439111", 3, "2023-06-06", "Спасибо", new String[]{"GREY"})},// grey
                {new Order("Иван", "Жиглов", "Москва флотская 4", "4", "89035439212", 3, "2023-06-06", "Спасибо", new String[]{"BLACK"})},// black
                {new Order("Олег", "Шарапов", "Москва флотская 5", "4", "89035439313", 3, "2023-06-06", "Спасибо", new String[]{"GREY", "BLACK"})},// grey and black
                {new Order("Андрей", "Фомин", "Москва флотская 6", "4", "89035439414", 3, "2023-06-06", "Спасибо", new String[]{})},// null
        };
    }

    @Test
    @DisplayName("Creating a courier twice")
    @Description("fgd")
    public void creatingOrder(){ // Создание заказа
        orderAPI.setOrder(order);
        orderAPI.createOrder().then().assertThat().body("track", notNullValue()).
                and().statusCode(201);
    }

}
