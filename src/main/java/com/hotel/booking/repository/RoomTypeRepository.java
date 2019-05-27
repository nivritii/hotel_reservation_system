package com.hotel.booking.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hotel.booking.entitymodel.ReservedRoomType;
import com.hotel.booking.entitymodel.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT new com.hotel.booking.entitymodel.ReservedRoomType(r.roomTypeId, SUM(r.quantity)) " +
            "FROM Reservation r WHERE " +
            "r.cancelled = FALSE AND " +
            "((?1 BETWEEN r.startDate AND r.endDate) OR " +
            "(?2 BETWEEN r.startDate AND r.endDate) OR " +
            "(?1 <= r.startDate AND ?2 >= r.endDate)) " +
            "GROUP BY r.roomTypeId")
    List<ReservedRoomType> findReservedRoomTypes(LocalDate start, LocalDate end);

    List<RoomType> findByQuantityGreaterThan(Integer value);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE com.hotel.booking.entitymodel.RoomType rt " +
            "SET rt.type = ?2, " +
            "rt.description = ?3, " +
            "rt.image = ?4, " +
            "rt.quantity = ?5, " +
            "rt.price = ?6 " +
            "WHERE rt.id = ?1")
    int updateRoomType(
            @Param("id") int id,
            @Param("type") String type,
            @Param("desc") String desc,
            @Param("image") String image,
            @Param("quantity") int quantity,
            @Param("price") BigDecimal price);

    @Query(value = "SELECT r from RoomType r WHERE r.type = ?1")
    Optional<RoomType> findByType(String type);
}
