package com.hotel.booking.exception;

public class RoomTypeNotAvailableException extends HrsException {
    public RoomTypeNotAvailableException() {
        super("All the rooms with type you specified have been occupied");
    }

    @Override
    public String getErrorCode() {
        return "room_type_not_available";
    }
}
