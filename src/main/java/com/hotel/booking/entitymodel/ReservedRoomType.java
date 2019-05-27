package com.hotel.booking.entitymodel;

import javax.validation.constraints.NotNull;

public class ReservedRoomType {

    @NotNull
    private Integer id;

    @NotNull
    private Long reservedQuantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Long reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public ReservedRoomType(@NotNull Integer id, @NotNull Long reservedQuantity) {
        this.id = id;
        this.reservedQuantity = reservedQuantity;
    }

    public ReservedRoomType() {
    }
}
