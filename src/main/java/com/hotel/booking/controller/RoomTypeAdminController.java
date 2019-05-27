package com.hotel.booking.controller;

import com.hotel.booking.entitymodel.RoomType;
import com.hotel.booking.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin/room-type")
public class RoomTypeAdminController {

    private RoomTypeService roomTypeService;

    @Autowired
    public RoomTypeAdminController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @PostMapping("create")
    public ResponseEntity<RoomType> create(@Validated @RequestBody RoomType roomType) {
        return new ResponseEntity<>(roomTypeService.save(roomType), HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<RoomType> update(@PathVariable Integer id, @Validated @RequestBody RoomType roomType) {
        return new ResponseEntity<>(roomTypeService.update(id, roomType),HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        roomTypeService.delete(id);
    }

    @GetMapping("{id}")
    public RoomType getRoomTypeById(@PathVariable Integer id) {
        return roomTypeService.findById(id);
    }

    @GetMapping("all")
    public Iterable<RoomType> getAllRoomTypes() {
        return roomTypeService.findAll();
    }
}
