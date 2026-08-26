package org.example.customerservice.repositories;

import org.example.customerservice.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    //Optional<CustomerEntity> findById(Long id);


}
