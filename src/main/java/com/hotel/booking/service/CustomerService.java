package com.hotel.booking.service;

import com.hotel.booking.entitymodel.Customer;
import com.hotel.booking.exception.NotFoundException;
import com.hotel.booking.exception.UsernameNotUniqueException;
import com.hotel.booking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(Customer.class, username));
    }

    public Customer findById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Customer.class, id.toString()));
    }

    @Transactional
    public Customer save(Customer form) {
        Customer customer = new Customer();
        try {
            customer = findByUsername(form.getUsername());
        } catch (NotFoundException ignored) {}
        if(customer.getId()!=null) throw new UsernameNotUniqueException();
        customer.setUsername(form.getUsername());
        customer.setPassword(form.getPassword());
        customer.setName(form.getName());

        return customerRepository.save(form);
    }

    @Transactional
    public Customer update(int id, Customer customer){
        Customer c = null;
        Customer customerToUpdate = findById(id);

        if(!Objects.equals(customerToUpdate.getUsername(), customer.getUsername())){
            try {
                customer = findByUsername(customerToUpdate.getUsername());
            } catch (NotFoundException ignored) {}
            if(customer.getId()!=null) throw new UsernameNotUniqueException();
        }

        customerToUpdate.setUsername(customer.getUsername());
        customerToUpdate.setName(customer.getName());
        customerToUpdate.setPassword(customer.getPassword());

        int updated = customerRepository.updateCustomer(
                id,
                customerToUpdate.getUsername(),
                customerToUpdate.getPassword(),
                customerToUpdate.getName()
        );
        if (updated == 1) c = customerToUpdate;
        return c;
    }

    @Transactional
    public void delete(Integer id) {
        findById(id);
        customerRepository.deleteById(id);
    }

}
