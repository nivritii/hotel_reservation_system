package com.hotel.booking.service;

import com.hotel.booking.entitymodel.Reservation;
import com.hotel.booking.entitymodel.RoomType;
import com.hotel.booking.exception.AvailableRoomsNotEnoughException;
import com.hotel.booking.exception.NotFoundException;
import com.hotel.booking.exception.ReservationStartDateHasPassedException;
import com.hotel.booking.repository.ReservationRepository;
import com.hotel.booking.repository.RoomTypeRepository;
import com.hotel.booking.validator.ReservationValidationHelper;
import com.hotel.booking.viewmodel.ReservationVOModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RoomTypeRepository roomTypeRepository) {
        this.reservationRepository = reservationRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    @Transactional
    public Reservation create(ReservationVOModel request) {
        ReservationValidationHelper.validateReservationTime(request.getStartDate(), request.getEndDate());

        validateRoomAvailability(request);

        final Reservation reservation = new Reservation();
        reservation.setRoomTypeId(request.getRoomTypeId());
        reservation.setCustomerId(request.getCustomerId());
        reservation.setQuantity(request.getQuantity());
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());
        reservation.setCancelled(Boolean.FALSE);

        return reservationRepository.save(reservation);
    }

    public Iterable<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation find(Integer id) {
        return reservationRepository.findByIdAndAndCancelled(id, Boolean.FALSE)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Reservation with id %d is not found or it has been cancelled", id)));
    }

    public Reservation findForUpdate(Integer id) {
        return reservationRepository.findByIdAndAndCancelledForUpdate(id, Boolean.FALSE)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Reservation with id %d is not found or it has been cancelled", id)));
    }

    @Transactional
    public Reservation update(Integer id, ReservationVOModel request) {
        ReservationValidationHelper.validateReservationTime(request.getStartDate(), request.getEndDate());

        final Reservation reservation = findForUpdate(id);

        if (ZonedDateTime.now().isAfter(reservation.getStartDate())) {
            throw new ReservationStartDateHasPassedException();
        }

        final RoomType currentRoomType = getRoomType(reservation.getRoomTypeId());

        if (!request.getRoomTypeId().equals(currentRoomType.getId())) {
            validateRoomAvailability(request);
            reservation.setRoomTypeId(request.getRoomTypeId());
        }

        reservation.setCustomerId(request.getCustomerId());
        reservation.setQuantity(request.getQuantity());
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());

        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation cancel(Integer id) {
        final Reservation reservation = findForUpdate(id);

        reservation.setCancelled(Boolean.TRUE);

        return reservationRepository.save(reservation);
    }

    private void validateRoomAvailability(ReservationVOModel request) {
        final RoomType roomType = getRoomType(request.getRoomTypeId());

        final List<Reservation> reservations = reservationRepository.find(roomType.getId(),
                request.getStartDate(), request.getEndDate());

        final Integer reservedQuantity = reservations.stream()
                .mapToInt(Reservation::getQuantity)
                .sum();

        if (request.getQuantity() > (roomType.getQuantity() - reservedQuantity)) {
            throw new AvailableRoomsNotEnoughException();
        }
    }

    private RoomType getRoomType(Integer id) {
        return roomTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RoomType.class, id.toString()));
    }
}
