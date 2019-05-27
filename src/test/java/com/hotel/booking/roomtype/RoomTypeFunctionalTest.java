package com.hotel.booking.roomtype;

import com.hotel.booking.FunctionalTest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoomTypeFunctionalTest extends FunctionalTest {

    @Test
    public void testFindAvailable_Success() {
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/room-type/available?start=2020-03-21&end=2020-03-22")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", hasItems(roomTypeId))
                .body("type", hasItems("test"))
                .body("description", hasItems("test"))
                .body("image", hasItems("test"))
                .body("quantity", hasItems(100))
                .body("price", hasItems(1000f));

        roomTypeTestHelper.deleteRoomType(roomTypeId);
    }

    @Test
    public void testFindAvailable_SomeRoomTypesAlreadyReserved() {
        LocalDate start = LocalDate.parse("2020-03-21");
        LocalDate end = LocalDate.parse("2020-03-22");
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");
        Integer reservationId = reservationTestHelper
                .createReservation(roomTypeId, customerId, 50, start, end, Boolean.FALSE);

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/room-type/available?start=2020-03-21&end=2020-03-22")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", hasItems(roomTypeId))
                .body("type", hasItems("test"))
                .body("description", hasItems("test"))
                .body("image", hasItems("test"))
                .body("quantity", hasItems(50))
                .body("price", hasItems(1000f));
        reservationTestHelper.deleteReservation(reservationId);
        roomTypeTestHelper.deleteRoomType(roomTypeId);
        customerTestHelper.deleteCustomer(customerId);

    }

    @Test
    public void testFindAvailable_StartDateAfterEndDate() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/room-type/available?start=2020-03-23&end=2020-03-22")
                .then()
                .statusCode(400)
                .body("error", equalTo("start_date_after_end_date"))
                .body("errorDescription", equalTo("Start date must be before end date"));
    }

    @Test
    public void testFindAvailable_DateSpecifiedAlreadyPassed() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/room-type/available?start=2019-03-21&end=2019-03-22")
                .then()
                .statusCode(400)
                .body("error", equalTo("date_specified_already_passed"))
                .body("errorDescription", equalTo("Start date and end date must be in the future"));
    }
}
