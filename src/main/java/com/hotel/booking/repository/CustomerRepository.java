package com.hotel.booking.repository;

import com.hotel.booking.entitymodel.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT c from Customer c WHERE c.username = ?1")
    Optional<Customer> findByUsername(String username);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE com.hotel.booking.entitymodel.Customer c " +
            "SET c.username = ?2, " +
            "c.password = ?3, " +
            "c.name = ?4 " +
            "WHERE c.id = ?1")
    int updateCustomer(
            @Param("id") int id,
            @Param("username") String username,
            @Param("password") String password,
            @Param("name") String name);
}
