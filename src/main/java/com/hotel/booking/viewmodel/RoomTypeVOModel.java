package com.hotel.booking.viewmodel;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.hotel.booking.entitymodel.RoomType;
import org.hibernate.validator.constraints.NotBlank;

public class RoomTypeVOModel {

    @NotBlank
    private String type;

    private String description;
    private String image;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal price;

    public RoomTypeVOModel() {
    }

    public RoomTypeVOModel(RoomType roomType) {
        this.type = roomType.getType();
        this.description = roomType.getDescription();
        this.image = roomType.getImage();
        this.quantity = roomType.getQuantity();
        this.price = roomType.getPrice();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
