package com.hotel.booking.service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hotel.booking.entitymodel.RoomType;
import com.hotel.booking.exception.NotFoundException;
import com.hotel.booking.repository.RoomTypeRepository;
import com.hotel.booking.validator.ReservationValidationHelper;
import com.hotel.booking.viewmodel.AvailableRoomType;
import com.hotel.booking.viewmodel.ReservedRoomType;
import com.hotel.booking.viewmodel.RoomTypeVOModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomTypeService {
  
	private final RoomTypeRepository roomTypeRepository;

    @Autowired
    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    public Iterable<RoomType> findAll() {
        return roomTypeRepository.findAll();
    }

    public List<AvailableRoomType> findAvailableRoomTypes(ZonedDateTime start, ZonedDateTime end) {
        ReservationValidationHelper.validateReservationTime(start, end);

        final List<ReservedRoomType> reservedRoomTypes = roomTypeRepository.findReservedRoomTypes(start, end);
        final Map<Integer, Integer> reservedRoomTypeMap = new HashMap<>();

        reservedRoomTypes.forEach(reservedRoomType ->
                reservedRoomTypeMap.put(reservedRoomType.getId(), reservedRoomType.getReservedQuantity().intValue()));

        final List<AvailableRoomType> availableRoomTypes = roomTypeRepository
                .findByQuantityGreaterThan(0)
                .stream()
                .map(roomType -> {
                    if (reservedRoomTypeMap.containsKey(roomType.getId())) {
                        final Integer reservedQuantity = reservedRoomTypeMap.get(roomType.getId());
                        final Integer availableQuantity = roomType.getQuantity() - reservedQuantity;

                        return new AvailableRoomType(roomType, availableQuantity);
                    }

                    return new AvailableRoomType(roomType);
                })
                .filter(availableRoomType -> availableRoomType.getAvailableQuantity() > 0)
                .collect(Collectors.toList());

        return availableRoomTypes;
    }

    public RoomType findById(Integer id) {
        return roomTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RoomType.class, id.toString()));
    }

    @Transactional
    public void save(RoomTypeVOModel form) {
        final RoomType roomType = new RoomType();
        roomType.setType(form.getType());
        roomType.setDescription(form.getDescription());
        roomType.setImage(form.getImage());
        roomType.setQuantity(0);
        roomType.setPrice(form.getPrice());

        roomTypeRepository.save(roomType);
    }

    @Transactional
    public void delete(Integer id) {
        findById(id);
        roomTypeRepository.deleteById(id);
    }
}
