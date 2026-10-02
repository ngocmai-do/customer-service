package org.example.customerservice;

import org.example.customerservice.entity.CustomerEntity;
import org.example.customerservice.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    CustomerRepository customerRepository;


    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void createAndFetchUser(){
        CustomerEntity customer = new CustomerEntity();
        customer.setName("Mai");

        customerRepository.save(customer);

        List<CustomerEntity> customers = customerRepository.findAll();


        assertEquals(1, customers.size());
        assertEquals("Mai", customers.get(0).getName());
    }

}
