package org.example.customerservice.services;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.example.customerservice.dto.CustomerDTO;
import org.example.customerservice.entity.CustomerEntity;
import org.example.customerservice.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate = new RestTemplate();


    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public CustomerDTO getCustomerById(Long id) {
        return toDto(customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kunden hittades inte")));
    }

    public boolean deleteCustomer(Long id) {
        boolean hasBookings = customerHasBookings(id);
        if (hasBookings) {
            return false;
        }
        customerRepository.deleteById(id);
        return true;
    }

    private boolean customerHasBookings(Long customerId) {
        Boolean exists = restTemplate.getForObject(
                "http://bookingservice:8080/booking/customer/" + customerId + "/exists",
                Boolean.class
        );
        return Boolean.TRUE.equals(exists);
    }

    public CustomerDTO updateCustomer(CustomerDTO customerToUpdate) {

        CustomerEntity customer = customerRepository.findById(customerToUpdate.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Kunden hittades inte"));

        customer.setName(customerToUpdate.getName());
        customer.setEmail(customerToUpdate.getEmail());
        customer.setTel(customerToUpdate.getTel());

        return toDto(customerRepository.save(customer));
    }

    public CustomerDTO toDto(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerEntity.getId());
        customerDTO.setName(customerEntity.getName());
        customerDTO.setEmail(customerEntity.getEmail());
        customerDTO.setTel(customerEntity.getTel());
        return customerDTO;
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {

        CustomerEntity customer = new CustomerEntity();

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setTel(customerDTO.getTel());

        return toDto(customerRepository.save(customer));
    }


}
