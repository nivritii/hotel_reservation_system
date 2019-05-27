package com.hotel.booking;

import com.hotel.booking.entitymodel.Customer;
import com.hotel.booking.entitymodel.Reservation;
import com.hotel.booking.entitymodel.RoomType;
import com.hotel.booking.repository.CustomerRepository;
import com.hotel.booking.repository.ReservationRepository;
import com.hotel.booking.repository.RoomTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.math.BigDecimal;

import java.time.ZonedDateTime;

@SpringBootApplication
@EnableJpaAuditing
public class BookingApplication implements CommandLineRunner {
    private final RoomTypeRepository roomTypeRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

    public BookingApplication(RoomTypeRepository roomTypeRepository, ReservationRepository reservationRepository,
                              CustomerRepository customerRepository){
         this.reservationRepository = reservationRepository;
         this.roomTypeRepository = roomTypeRepository;
         this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
    }
}
