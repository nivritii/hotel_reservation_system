package com.hotel.booking.customer;

import com.hotel.booking.FunctionalTest;
import com.hotel.booking.entitymodel.Customer;
import com.hotel.booking.repository.CustomerRepository;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerFunctionalTest extends FunctionalTest {

    @Autowired
    public CustomerRepository customerRepository;

    @Test
    public void testCreate_Success() {
        Customer request = new Customer("test", "test", "test");

        Map response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post("/customer/create")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("username", equalTo( "test"))
                .body("password", equalTo( "test"))
                .body("name", equalTo( "test"))
                .extract().as(Map.class);

        assertThat(response).containsOnlyKeys(
                "id", "username", "password", "name");

        Optional<Customer> newCustomer = customerRepository
                .findById(Integer.valueOf(response.get("id").toString()));

        assertThat(newCustomer.isPresent());

        customerTestHelper.deleteCustomer(Integer.valueOf(response.get("id").toString()));
    }

    @Test
    public void testCreate_UserNameNotUnique(){
        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");

        Customer request = new Customer("test", "test", "test");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .post("/customer/create")
                .then()
                .statusCode(400)
                .body("error", equalTo("username_not_unique"))
                .body("errorDescription", equalTo("Username already exist"));

        customerTestHelper.deleteCustomer(customerId);
    }

    @Test
    public void testUpdate_Success() {
        Customer request = new Customer("test", "test","test test");
        Integer customerId = customerTestHelper.createCustomer("test", "test",
                "test");

        Map response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
                .when()
                .patch("/customer/"+customerId+"/update")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", equalTo(customerId))
                .body("username", equalTo( "test"))
                .body("password", equalTo( "test"))
                .body("name", equalTo( "test test"))
                .extract().as(Map.class);

        assertThat(response).containsOnlyKeys(
                "id", "username", "password", "name");

        Optional<Customer> updatedCustomer = customerRepository
                .findById(Integer.valueOf(response.get("id").toString()));
        assertThat(updatedCustomer.get().getId()).isEqualTo(customerId);

        customerTestHelper.deleteCustomer(customerId);

    }

    @Test
    public void testDelete_Success() {

        Integer customerId = customerTestHelper.createCustomer("test", "test", "test");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete("/customer/" + customerId + "/delete")
                .then()
                .statusCode(200);
    }
}
