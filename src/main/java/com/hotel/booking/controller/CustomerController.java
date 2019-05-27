package com.hotel.booking.controller;

import com.hotel.booking.entitymodel.Customer;
import com.hotel.booking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("create")
    public ResponseEntity<Customer> createCustomer(@Validated @RequestBody Customer request) {
        return new ResponseEntity<>(customerService.save(request), HttpStatus.CREATED);
    }

    @PatchMapping("{id}/update")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @Validated @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.update(id, customer),HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/delete")
    public void deleteCustomer(@PathVariable Integer id) {
        customerService.delete(id);
    }

    @GetMapping("{id}")
    public Customer getCustomerById(@PathVariable Integer id) {
        return customerService.findById(id);
    }

    @GetMapping("/username/{userName}")
    public Customer getCustomerByUsername(@PathVariable String userName) {
        return customerService.findByUsername(userName);
    }

    @GetMapping("all")
    public Iterable<Customer> getAllCustomers() {
        return customerService.findAll();
    }
}
