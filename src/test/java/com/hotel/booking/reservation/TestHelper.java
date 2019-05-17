package com.hotel.booking.reservation;

import com.hotel.booking.entitymodel.Reservation;
import com.hotel.booking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class TestHelper {

    @Autowired
    private ReservationRepository reservationRepository;

    public void cleanUp() {
    }

    public Integer createReservation(Integer roomTypeId, Integer customerId, Integer quantity,
                              ZonedDateTime startDate, ZonedDateTime endDate, Boolean cancelled) {
        final Reservation reservation = new Reservation();
        reservation.setRoomTypeId(roomTypeId);
        reservation.setCustomerId(customerId);
        reservation.setQuantity(quantity);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setCancelled(cancelled);

        final Reservation newReservation = reservationRepository.save(reservation);
        return newReservation.getId();
    }

}