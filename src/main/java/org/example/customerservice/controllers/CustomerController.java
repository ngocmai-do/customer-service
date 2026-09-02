package org.example.customerservice.controllers;

import jakarta.validation.Valid;
import org.example.customerservice.dto.CustomerDTO;
import org.example.customerservice.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers()); // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id)); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @PutMapping
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerToUpdate) {
        return ResponseEntity.ok(customerService.updateCustomer(customerToUpdate)); // 200
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(201).body(customerService.createCustomer(customerDTO)); // 201
    }
}