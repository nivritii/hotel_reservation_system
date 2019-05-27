package com.hotel.booking.roomtype;

import com.hotel.booking.entitymodel.RoomType;
import com.hotel.booking.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RoomTypeTestHelper {

    @Autowired
    private RoomTypeService roomTypeService;

    public Integer createRoomType(
            String type,
            Integer quantity,
            String desc,
            BigDecimal price,
            String image){
        RoomType roomType = new RoomType();
        roomType.setType(type);
        roomType.setQuantity(quantity);
        roomType.setDescription(desc);
        roomType.setPrice(price);
        roomType.setImage(image);

        RoomType newRoomType = roomTypeService.save(roomType);
        return newRoomType.getId();
    }

    public void deleteRoomType(Integer id) {
        roomTypeService.delete(id);
    }
}
