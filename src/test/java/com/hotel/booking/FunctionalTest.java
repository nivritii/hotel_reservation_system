package com.hotel.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.booking.reservation.TestHelper;
import io.restassured.RestAssured;
import io.restassured.internal.mapping.Jackson2Mapper;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BookingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FunctionalTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Value("${local.server.port}")
    protected int port;

    @Autowired
    protected TestHelper testHelper;

    @Before
    public void baseSetup() {
        RestAssured.port = port;
        RestAssured.objectMapper(new Jackson2Mapper((cls, charset) -> objectMapper));
    }

    @After
    public void cleanUp() {
        testHelper.cleanUp();
    }
}