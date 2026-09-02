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

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public CustomerDTO getCustomerById(Long id) {
        return toDto(customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kunden hittades inte")));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public CustomerDTO updateCustomer(CustomerDTO customerToUpdate) {
        CustomerEntity customer = customerRepository.findById(customerToUpdate.getId())
                .orElseThrow(() -> new IllegalArgumentException("Kunden hittades inte"));
        customer.setName(customerToUpdate.getName());
        customer.setEmail(customerToUpdate.getEmail());
        customer.setTel(customerToUpdate.getTel());
        return toDto(customerRepository.save(customer));
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        CustomerEntity customer = new CustomerEntity();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setTel(customerDTO.getTel());
        return toDto(customerRepository.save(customer));
    }

    private CustomerDTO toDto(CustomerEntity entity) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setTel(entity.getTel());
        return dto;
    }
}