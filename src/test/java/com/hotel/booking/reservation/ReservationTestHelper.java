package com.hotel.booking.reservation;

import com.hotel.booking.entitymodel.Reservation;
import com.hotel.booking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReservationTestHelper {

    @Autowired
    private ReservationService reservationService;

    public Integer createReservation(Integer roomTypeId, Integer customerId, Integer quantity,
                              LocalDate startDate, LocalDate endDate, Boolean cancelled) {
        final Reservation reservation = new Reservation();
        reservation.setRoomTypeId(roomTypeId);
        reservation.setCustomerId(customerId);
        reservation.setQuantity(quantity);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setCancelled(cancelled);

        final Reservation newReservation = reservationService.create(reservation);
        return newReservation.getId();
    }

    public void deleteReservation(Integer id) {
        reservationService.deleteReservation(id);
    }

}