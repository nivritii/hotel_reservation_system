package com.hotel.booking.viewmodel;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.hotel.booking.entitymodel.RoomType;
import org.hibernate.validator.constraints.NotBlank;

public class AvailableRoomType {

    @NotNull
    private Integer id;

    @NotBlank
    private String type;

    private String description;
    private String image;

    @NotNull
    private Integer availableQuantity;

    @NotNull
    private BigDecimal price;

    public AvailableRoomType(RoomType roomType, Integer availableQuantity) {
        this.id = roomType.getId();
        this.type = roomType.getType();
        this.description = roomType.getDescription();
        this.image = roomType.getImage();
        this.availableQuantity = availableQuantity;
        this.price = roomType.getPrice();
    }

    public AvailableRoomType(RoomType roomType) {
        this.id = roomType.getId();
        this.type = roomType.getType();
        this.description = roomType.getDescription();
        this.image = roomType.getImage();
        this.availableQuantity = roomType.getQuantity();
        this.price = roomType.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }


}
