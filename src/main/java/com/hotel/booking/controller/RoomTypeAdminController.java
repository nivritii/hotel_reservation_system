package com.hotel.booking.controller;

import com.hotel.booking.service.RoomTypeService;
import com.hotel.booking.viewmodel.RoomTypeVOModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/room-type")
public class RoomTypeAdminController {

    private RoomTypeService roomTypeService;

    @Autowired
    public RoomTypeAdminController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @PostMapping("create")
    public void create(RoomTypeVOModel roomTypeForm) {
        roomTypeService.save(roomTypeForm);
    }

    @PostMapping("update")
    public void update(RoomTypeVOModel roomType) {
        roomTypeService.save(roomType);
    }

    @GetMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        roomTypeService.delete(id);
    }
}
