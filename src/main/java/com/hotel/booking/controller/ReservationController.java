package com.hotel.booking.controller;

import com.hotel.booking.entitymodel.Reservation;
import com.hotel.booking.service.ReservationService;
import com.hotel.booking.viewmodel.ReservationVOModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@Validated @RequestBody ReservationVOModel request) {
        return new ResponseEntity<>(reservationService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public Reservation find(@PathVariable Integer id) {
        return reservationService.find(id);
    }

    @PatchMapping("{id}")
    public Reservation update(@PathVariable Integer id, @Validated @RequestBody ReservationVOModel request) {
        return reservationService.update(id, request);
    }

    @PatchMapping("{id}/cancel")
    public Reservation cancel(@PathVariable Integer id) {
        return reservationService.cancel(id);
    }
}
