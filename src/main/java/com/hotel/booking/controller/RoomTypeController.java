package com.hotel.booking.controller;

import com.hotel.booking.entitymodel.RoomType;
import com.hotel.booking.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.hotel.booking.constants.Constant.DATE_FORMAT;

@RestController
@RequestMapping("room-type")
public class RoomTypeController {

    private RoomTypeService roomTypeService;

    @Autowired
    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @GetMapping("available")
    public List<RoomType> findAvailableRoomTypes(@RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDate start,
                                                 @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDate end) {
        return roomTypeService.findAvailableRoomTypes(start, end);
    }
}
