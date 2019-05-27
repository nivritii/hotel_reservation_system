package com.hotel.booking.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hotel.booking.entitymodel.ReservedRoomType;
import com.hotel.booking.entitymodel.RoomType;
import com.hotel.booking.exception.NotFoundException;
import com.hotel.booking.repository.RoomTypeRepository;
import com.hotel.booking.validator.ReservationValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomTypeService {
  
	private RoomTypeRepository roomTypeRepository;

    @Autowired
    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    public Iterable<RoomType> findAll() {
        return roomTypeRepository.findAll();
    }

    @Transactional
    public List<RoomType> findAvailableRoomTypes(LocalDate start, LocalDate end) {
        ReservationValidationHelper.validateReservationTime(start, end);

        List<ReservedRoomType> reservedRoomTypes = roomTypeRepository.findReservedRoomTypes(start, end);
        Map<Integer, Integer> reservedRoomTypeMap = new HashMap<>();

        reservedRoomTypes.forEach(reservedRoomType ->
                reservedRoomTypeMap.put(reservedRoomType.getId(), reservedRoomType.getReservedQuantity().intValue()));

        List<RoomType> availableRoomTypes = roomTypeRepository
                .findByQuantityGreaterThan(0)
                .stream()
                .map(roomType -> {
                    if (reservedRoomTypeMap.containsKey(roomType.getId())) {
                        int reservedQuantity = reservedRoomTypeMap.get(roomType.getId());
                        int availableQuantity = roomType.getQuantity() - reservedQuantity;
                        roomType.setQuantity(availableQuantity);
                        return roomType;
                    }

                    return roomType;
                })
                .filter(availableRoomType -> availableRoomType.getQuantity() > 0)
                .collect(Collectors.toList());

        return availableRoomTypes;
    }

    public RoomType findById(Integer id) {
        return roomTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RoomType.class, id.toString()));
    }

    @Transactional
    public RoomType save(RoomType type) {
        final RoomType roomType = new RoomType();
        roomType.setType(type.getType());
        roomType.setDescription(type.getDescription());
        roomType.setImage(type.getImage());
        roomType.setQuantity(type.getQuantity());
        roomType.setPrice(type.getPrice());

        return roomTypeRepository.save(roomType);
    }

    @Transactional
    public RoomType update(int id, RoomType type){
        RoomType rt = null;
        RoomType roomToUpdate = findById(id);

        roomToUpdate.setType(type.getType());
        roomToUpdate.setDescription(type.getDescription());
        roomToUpdate.setImage(type.getImage());
        roomToUpdate.setQuantity(type.getQuantity());
        roomToUpdate.setPrice(type.getPrice());

        int updated = roomTypeRepository.updateRoomType(
          id,
          roomToUpdate.getType(),
          roomToUpdate.getDescription(),
          roomToUpdate.getImage(),
          roomToUpdate.getQuantity(),
          roomToUpdate.getPrice()
        );
        if (updated == 1) rt = roomToUpdate;
        return rt;
    }

    @Transactional
    public void delete(Integer id) {
        findById(id);
        roomTypeRepository.deleteById(id);
    }
}
