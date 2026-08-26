package org.example.customerservice.services;

import org.example.customerservice.dto.CustomerDTO;
import org.example.customerservice.entity.CustomerEntity;
import org.example.customerservice.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerEntity getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kunden hittades inte"));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public CustomerEntity updateCustomer(CustomerDTO customerToUpdate) {

        CustomerEntity customer = customerRepository.findById(customerToUpdate.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Kunden hittades inte"));

        customer.setName(customerToUpdate.getName());
        customer.setEmail(customerToUpdate.getEmail());
        customer.setTel(customerToUpdate.getTel());

        return customerRepository.save(customer);
    }

    public CustomerEntity createCustomer(CustomerDTO customerDTO) {

        CustomerEntity customer = new CustomerEntity();

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setTel(customerDTO.getTel());

        return customerRepository.save(customer);
    }


}
