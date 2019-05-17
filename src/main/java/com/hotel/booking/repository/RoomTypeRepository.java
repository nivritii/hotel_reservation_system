package com.hotel.booking.repository;

import java.time.ZonedDateTime;
import java.util.List;

import com.hotel.booking.entitymodel.RoomType;
import com.hotel.booking.viewmodel.ReservedRoomType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends CrudRepository<RoomType, Integer> {
    @Query(value = "SELECT new com.hotel.booking.viewmodel.ReservedRoomType(r.roomTypeId, SUM(r.quantity)) " +
            "FROM Reservation r WHERE " +
            "r.cancelled = FALSE AND " +
            "((?1 BETWEEN r.startDate AND r.endDate) OR " +
            "(?2 BETWEEN r.startDate AND r.endDate) OR " +
            "(?1 <= r.startDate AND ?2 >= r.endDate)) " +
            "GROUP BY r.roomTypeId")
    List<ReservedRoomType> findReservedRoomTypes(ZonedDateTime start, ZonedDateTime end);

    List<RoomType> findByQuantityGreaterThan(Integer value);
}
