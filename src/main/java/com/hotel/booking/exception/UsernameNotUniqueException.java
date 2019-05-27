package com.hotel.booking.exception;

public class UsernameNotUniqueException extends HrsException{
    public UsernameNotUniqueException() {
        super("Username already exist");
    }

    @Override
    public String getErrorCode() {
        return "username_not_unique";
    }
}
