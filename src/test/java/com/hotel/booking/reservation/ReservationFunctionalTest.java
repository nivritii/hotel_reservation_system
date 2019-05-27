package com.hotel.booking.reservation;

import com.hotel.booking.FunctionalTest;
import com.hotel.booking.entitymodel.Reservation;
import com.hotel.booking.repository.ReservationRepository;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(SpringJUnit4ClassRunner.class)
public class ReservationFunctionalTest extends FunctionalTest {

    @Autowired
    public ReservationRepository reservationRepository;

    @Test
    public void testCreate_Success() {
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");

        Reservation request = new Reservation(roomTypeId, customerId, 1,
                LocalDate.parse("2100-01-01"), LocalDate.parse("2100-01-04"));

        Map response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                    .post("/reservation/create")
                .then()
                    .statusCode(201)
                    .contentType(ContentType.JSON)
                    .body("id", notNullValue())
                    .body("roomTypeId", equalTo(roomTypeId))
                    .body("customerId", equalTo(customerId))
                    .body("quantity", equalTo(1))
                    .body("cancelled", equalTo(Boolean.FALSE))
                    .body("createdAt", notNullValue())
                    .body("updatedAt", notNullValue())
                    .extract().as(Map.class);

        assertThat(response).containsOnlyKeys(
                "id", "roomTypeId", "customerId", "quantity", "startDate", "endDate", "cancelled", "createdAt",
                "updatedAt");

        Optional<Reservation> newReservation = reservationRepository
                .findById(Integer.valueOf(response.get("id").toString()));

        assertThat(newReservation.isPresent());

        reservationTestHelper.deleteReservation(Integer.valueOf(response.get("id").toString()));
        roomTypeTestHelper.deleteRoomType(roomTypeId);
        customerTestHelper.deleteCustomer(customerId);
    }

    @Test
    public void testCreate_StartDateAfterEndDate() {
        Reservation request = new Reservation(1, 1, 1,
                LocalDate.parse("2101-01-01", DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse("2100-01-04", DateTimeFormatter.ISO_LOCAL_DATE));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                    .post("/reservation/create")
                .then()
                    .statusCode(400)
                    .body("error", equalTo("start_date_after_end_date"))
                    .body("errorDescription", equalTo("Start date must be before end date"));
    }

    @Test
    public void testCreate_DateSpecifiedAlreadyPassed() {
        Reservation request = new Reservation(1, 1, 1,
                LocalDate.parse("2001-01-01", DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse("2100-01-04", DateTimeFormatter.ISO_LOCAL_DATE));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                    .post("/reservation/create")
                .then()
                    .statusCode(400)
                    .body("error", equalTo("date_specified_already_passed"))
                    .body("errorDescription", equalTo("Start date and end date must be in the future"));
    }

    @Test
    public void testCreate_AvailableRoomsNotEnough() {
        LocalDate start = LocalDate.parse("2020-03-21");
        LocalDate end = LocalDate.parse("2020-03-22");
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");
        Integer reservationId = reservationTestHelper
                .createReservation(roomTypeId, customerId, 50, start, end, Boolean.FALSE);

        Reservation request = new Reservation(roomTypeId, customerId, 2000,
                LocalDate.parse("2100-01-01"), LocalDate.parse("2100-01-04"));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                    .post("/reservation/create")
                .then()
                    .statusCode(400)
                    .body("error", equalTo("available_rooms_not_enough"))
                    .body("errorDescription", equalTo("Quantity requested is bigger than the " +
                            "available rooms for the specified type"));

        reservationTestHelper.deleteReservation(reservationId);
        roomTypeTestHelper.deleteRoomType(roomTypeId);
        customerTestHelper.deleteCustomer(customerId);
    }

    @Test
    public void testFind_Success() {
        LocalDate start = LocalDate.parse("2020-03-21");
        LocalDate end = LocalDate.parse("2020-03-22");
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");
        Integer reservationId = reservationTestHelper
                .createReservation(roomTypeId, customerId, 50, start, end, Boolean.FALSE);

        Map response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                    .get("/reservation/" + reservationId.toString())
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("id", equalTo(reservationId))
                    .body("roomTypeId", equalTo(roomTypeId))
                    .body("customerId", equalTo(customerId))
                    .body("quantity", equalTo(50))
                    .body("cancelled", equalTo(Boolean.FALSE))
                    .body("createdAt", notNullValue())
                    .body("updatedAt", notNullValue())
                    .extract().as(Map.class);

        assertThat(response).containsOnlyKeys(
                "id", "roomTypeId", "customerId", "quantity", "startDate", "endDate", "cancelled", "createdAt",
                "updatedAt");

        reservationTestHelper.deleteReservation(reservationId);
        roomTypeTestHelper.deleteRoomType(roomTypeId);
        customerTestHelper.deleteCustomer(customerId);
    }

    @Test
    public void testFind_NotFound() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                    .get("/reservation/99999")
                .then()
                    .statusCode(400)
                    .body("error", equalTo("not_found"))
                    .body("errorDescription", equalTo("Reservation with id 99999 is not found or it has " +
                            "been cancelled"));
    }

    @Test
    public void testUpdate_Success() {
        LocalDate start = LocalDate.parse("2020-03-21");
        LocalDate end = LocalDate.parse("2020-03-22");
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");
        Integer reservationId = reservationTestHelper
                .createReservation(roomTypeId, customerId, 50, start, end, Boolean.FALSE);

        Reservation request = new Reservation(roomTypeId, customerId, 10,
                LocalDate.parse("2100-01-01"), LocalDate.parse("2100-01-04"));

        Map response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                    .patch("/reservation/" + reservationId.toString() + "/update")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("id", equalTo(reservationId))
                    .body("roomTypeId", equalTo(roomTypeId))
                    .body("customerId", equalTo(customerId))
                    .body("quantity", equalTo(10))
                    .body("createdAt", notNullValue())
                    .body("updatedAt", notNullValue())
                    .extract().as(Map.class);

        assertThat(response).containsOnlyKeys(
                "id", "roomTypeId", "customerId", "quantity", "startDate", "endDate", "cancelled", "createdAt",
                "updatedAt");

        Optional<Reservation> newReservation = reservationRepository
                .findById(Integer.valueOf(response.get("id").toString()));

        assertThat(newReservation.get().getQuantity()).isEqualTo(10);

        reservationTestHelper.deleteReservation(reservationId);
        roomTypeTestHelper.deleteRoomType(roomTypeId);
        customerTestHelper.deleteCustomer(customerId);
    }

    @Test
    public void testUpdate_StartDateAfterEndDate() {
        LocalDate start = LocalDate.parse("2020-03-21");
        LocalDate end = LocalDate.parse("2020-03-22");
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");
        Integer reservationId = reservationTestHelper
                .createReservation(roomTypeId, customerId, 50, start, end, Boolean.FALSE);

        Reservation request = new Reservation(roomTypeId, customerId, 10,
                LocalDate.parse("2101-01-01"), LocalDate.parse("2100-01-04"));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                    .patch("/reservation/"+ reservationId.toString() + "/update")
                .then()
                    .statusCode(400)
                    .body("error", equalTo("start_date_after_end_date"))
                    .body("errorDescription", equalTo("Start date must be before end date"));

        reservationTestHelper.deleteReservation(reservationId);
        roomTypeTestHelper.deleteRoomType(roomTypeId);
        customerTestHelper.deleteCustomer(customerId);
    }

    @Test
    public void testUpdate_DateSpecifiedAlreadyPassed() {
        LocalDate start = LocalDate.parse("2020-03-21");
        LocalDate end = LocalDate.parse("2020-03-22");
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");
        Integer reservationId = reservationTestHelper
                .createReservation(roomTypeId, customerId, 50, start, end, Boolean.FALSE);

        Reservation request = new Reservation(roomTypeId, customerId, 10,
                LocalDate.parse("2000-01-01"), LocalDate.parse("2100-01-04"));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                    .patch("/reservation/" + reservationId.toString() + "/update")
                .then()
                    .statusCode(400)
                    .body("error", equalTo("date_specified_already_passed"))
                    .body("errorDescription", equalTo("Start date and end date must be in the future"));

        reservationTestHelper.deleteReservation(reservationId);
        roomTypeTestHelper.deleteRoomType(roomTypeId);
        customerTestHelper.deleteCustomer(customerId);
    }

    @Test
    public void testUpdate_AvailableRoomsNotEnough() {
        LocalDate start = LocalDate.parse("2020-03-21");
        LocalDate end = LocalDate.parse("2020-03-22");
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");
        Integer reservationId = reservationTestHelper
                .createReservation(roomTypeId, customerId, 50, start, end, Boolean.FALSE);

        Reservation request = new Reservation(roomTypeId, customerId, 2000,
                LocalDate.parse("2100-01-01"), LocalDate.parse("2100-01-04"));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                    .patch("/reservation/" + reservationId.toString() + "/update")
                .then()
                    .statusCode(400)
                    .body("error", equalTo("available_rooms_not_enough"))
                    .body("errorDescription", equalTo("Quantity requested is bigger than the " +
                            "available rooms for the specified type"));

        reservationTestHelper.deleteReservation(reservationId);
        roomTypeTestHelper.deleteRoomType(roomTypeId);
        customerTestHelper.deleteCustomer(customerId);
    }

    @Test
    public void testUpdate_NotFound() {
        LocalDate start = LocalDate.parse("2020-03-21");
        LocalDate end = LocalDate.parse("2020-03-22");
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");
        Integer reservationId = reservationTestHelper
                .createReservation(roomTypeId, customerId, 50, start, end, Boolean.FALSE);

        final Reservation request = new Reservation(roomTypeId, customerId, 1,
                LocalDate.parse("2100-01-01", DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse("2100-01-04", DateTimeFormatter.ISO_LOCAL_DATE));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                    .patch("/reservation/99999/update")
                .then()
                    .statusCode(400)
                    .body("error", equalTo("not_found"))
                    .body("errorDescription", equalTo("Reservation with id 99999 is not found or it has " +
                            "been cancelled"));

        reservationTestHelper.deleteReservation(reservationId);
        roomTypeTestHelper.deleteRoomType(roomTypeId);
        customerTestHelper.deleteCustomer(customerId);
    }

    @Test
    public void testUpdate_RoomTypeNotFound() {
        LocalDate start = LocalDate.parse("2020-03-21");
        LocalDate end = LocalDate.parse("2020-03-22");
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");
        Integer reservationId = reservationTestHelper
                .createReservation(roomTypeId, customerId, 50, start, end, Boolean.FALSE);

        Reservation request = new Reservation(99, customerId, 1,
                LocalDate.parse("2100-01-01"), LocalDate.parse("2100-01-04"));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                    .patch("/reservation/" + reservationId.toString() + "/update")
                .then()
                    .statusCode(400)
                    .body("error", equalTo("not_found"))
                    .body("errorDescription", equalTo("RoomType with Id 99 is not found"));

        reservationTestHelper.deleteReservation(reservationId);
        roomTypeTestHelper.deleteRoomType(roomTypeId);
        customerTestHelper.deleteCustomer(customerId);
    }

    @Test
    public void testCancel_Success() {
        LocalDate start = LocalDate.parse("2020-03-21", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate end = LocalDate.parse("2020-03-22", DateTimeFormatter.ISO_LOCAL_DATE);
        Integer roomTypeId = roomTypeTestHelper
                .createRoomType("test", 100,"test", BigDecimal.valueOf(1000f),"test");
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");
        Integer reservationId = reservationTestHelper
                .createReservation(roomTypeId, customerId, 50, start, end, Boolean.FALSE);

        Map response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                    .patch("/reservation/" + reservationId.toString() + "/cancel")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("id", equalTo(reservationId))
                    .body("roomTypeId", equalTo(roomTypeId))
                    .body("customerId", equalTo(customerId))
                    .body("quantity", equalTo(50))
                    .body("cancelled", equalTo(Boolean.TRUE))
                    .body("createdAt", notNullValue())
                    .body("updatedAt", notNullValue())
                    .extract().as(Map.class);

        assertThat(response).containsOnlyKeys(
                "id", "roomTypeId", "customerId", "quantity", "startDate", "endDate", "cancelled", "createdAt",
                "updatedAt");

        Optional<Reservation> newReservation = reservationRepository
                .findById(Integer.valueOf(response.get("id").toString()));

        assertThat(newReservation.get().getCancelled()).isEqualTo(Boolean.TRUE);

        reservationTestHelper.deleteReservation(reservationId);
        roomTypeTestHelper.deleteRoomType(roomTypeId);
        customerTestHelper.deleteCustomer(customerId);
    }

    @Test
    public void testCancel_NotFound() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                    .patch("/reservation/9999/cancel")
                .then()
                    .statusCode(400)
                    .body("error", equalTo("not_found"))
                    .body("errorDescription", equalTo("Reservation with id 9999 is not found or it has " +
                            "been cancelled"));
    }
}