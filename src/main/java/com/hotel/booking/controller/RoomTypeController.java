package com.hotel.booking.controller;

import com.hotel.booking.service.RoomTypeService;
import com.hotel.booking.viewmodel.AvailableRoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

import static com.hotel.booking.constants.Constant.DATE_FORMAT;
import static com.hotel.booking.constants.Constant.ZONE_OFFSET;

@RestController
@RequestMapping("room-types")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @Autowired
    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @GetMapping("available")
    public List<AvailableRoomType> findAvailableRoomTypes(@RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDate start,
                                                          @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDate end) {
        return roomTypeService.findAvailableRoomTypes(ZonedDateTime.of(start, LocalTime.MIN, ZONE_OFFSET),
                ZonedDateTime.of(end, LocalTime.MIN, ZONE_OFFSET));
    }
}
