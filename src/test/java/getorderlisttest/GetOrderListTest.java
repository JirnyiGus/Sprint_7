package getorderlisttest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pens.OrderAPI;
import url.BaseURL;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {

    OrderAPI orderAPI = new OrderAPI();
    BaseURL baseURL = new BaseURL();

    @Before
    public void setUp() {
        baseURL.setUp();
    }

    @Test // Получение списка заказов
    @DisplayName("Get order list")
    @Description("Checking for a list of orders")
    public void getOrderList() {
        orderAPI.getListOrder().then().assertThat().body("orders", notNullValue()).
                and().statusCode(200);
    }

}
