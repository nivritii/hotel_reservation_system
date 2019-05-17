package com.hotel.booking.exception;

public class StartDateAfterEndDateException extends HrsException {
    public StartDateAfterEndDateException() {
        super("Start date must be before end date");
    }

    @Override
    public String getErrorCode() {
        return "start_date_after_end_date";
    }
}
