package com.himusharier.bookstore.orders.web.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import com.himusharier.bookstore.orders.AbstractIT;
import com.himusharier.bookstore.orders.testdata.TestDataFactory;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OrderControllerTests extends AbstractIT {

    @Nested
    class CreateOrderTests {
        @Test
        void shouldCreateOrderSuccessfully() {
            mockGetProductByCode("P100", "Product 1", new BigDecimal("25.50"));
            var payload =
                    """
                        {
                             "customer" : {
                                 "name": "sharier",
                                 "email" : "himusharier@gmail.com",
                                 "phone" : "01867849219"
                             },
                             "deliveryAddress" : {
                                 "addressLine1" : "Ananda Nagar",
                                 "addressLine2" : "Merul Badda",
                                 "city" : "Dhaka",
                                 "state" : "Dhaka North",
                                 "zipCode" : "1212",
                                 "country" : "Bangladesh"
                             },
                             "items" : [
                                 {
                                     "code" : "P100",
                                     "name" : "Product 1",
                                     "price" : 25.50,
                                     "quantity" : 1
                                 }
                             ]
                        }
                    """;
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
