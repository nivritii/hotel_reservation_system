package com.hotel.booking.viewmodel;

import javax.validation.constraints.NotNull;

public class ReservedRoomType {

    @NotNull
    private Integer id;

    @NotNull
    private Long reservedQuantity;

    public ReservedRoomType() {
        super();
    }

    public ReservedRoomType(@NotNull Integer id, @NotNull Long reservedQuantity) {
        super();
        this.id = id;
        this.reservedQuantity = reservedQuantity;
    }

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

}
