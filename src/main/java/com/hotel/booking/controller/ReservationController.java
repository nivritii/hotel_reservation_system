package com.hotel.booking.controller;

import com.hotel.booking.entitymodel.Reservation;
import com.hotel.booking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("create")
    public ResponseEntity<Reservation> createReservation(@Validated @RequestBody Reservation request) {
        return new ResponseEntity<>(reservationService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public Reservation findReservationById(@PathVariable Integer id) {
        return reservationService.find(id);
    }

    @GetMapping("all")
    public Iterable<Reservation> findAllReservations() {
        return reservationService.findAll();
    }

    @PatchMapping("{id}/update")
    public Reservation updateReservation(@PathVariable Integer id, @Validated @RequestBody Reservation request) {
        return reservationService.update(id, request);
    }

    @PatchMapping("{id}/cancel")
    public Reservation cancelReservation(@PathVariable Integer id) {
        return reservationService.cancel(id);
    }
}
