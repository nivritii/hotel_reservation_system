package com.hotel.booking.viewmodel;

import com.hotel.booking.util.ZonedDateTimeConverter;

import javax.persistence.Convert;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class ReservationVOModel {

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public ReservationVOModel() {
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public ReservationVOModel(@NotNull Integer roomTypeId, @NotNull Integer customerId,
                              @NotNull Integer quantity, ZonedDateTime startDate, ZonedDateTime endDate) {
        this.roomTypeId = roomTypeId;
        this.customerId = customerId;
        this.quantity = quantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @NotNull
    private Integer roomTypeId;

    @NotNull
    private Integer customerId;

    @NotNull
    private Integer quantity;

    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime startDate;

    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime endDate;
}
