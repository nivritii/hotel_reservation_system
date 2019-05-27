package com.hotel.booking.validator;

import com.hotel.booking.exception.DateSpecifiedAlreadyPassedException;
import com.hotel.booking.exception.StartDateAfterEndDateException;

import java.time.LocalDate;

public final class ReservationValidationHelper {

    private ReservationValidationHelper() {
    }

    public static void validateReservationTime(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new StartDateAfterEndDateException();
        }

        if (LocalDate.now().isAfter(start)) {
            throw new DateSpecifiedAlreadyPassedException();
        }
    }
}
