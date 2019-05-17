package com.hotel.booking.exception;

public class AvailableRoomsNotEnoughException extends HrsException {
    public AvailableRoomsNotEnoughException() {
        super("Quantity requested is bigger than the available rooms for " +
                "the specified type ");
    }

    @Override
    public String getErrorCode() {
        return "available_rooms_not_enough";
    }
}
