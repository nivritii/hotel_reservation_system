package com.hotel.booking.customer;

import com.hotel.booking.entitymodel.Customer;
import com.hotel.booking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerTestHelper {

    @Autowired
    private CustomerService customerService;

    public Integer createCustomer(String username, String password, String name){
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setName(name);

        Customer newCustomer = customerService.save(customer);
        return newCustomer.getId();
    }

    public void deleteCustomer(Integer id) {
        customerService.delete(id);
    }
}
