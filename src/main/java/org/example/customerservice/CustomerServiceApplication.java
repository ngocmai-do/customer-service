package org.example.customerservice;

import org.example.customerservice.entity.CustomerEntity;
import org.example.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepo){
        return (args) -> {
            CustomerEntity c1 = new CustomerEntity("Peder Lindholm","peder@gmail.com","0123456789");
            CustomerEntity c2 = new CustomerEntity("Farah Sleiman","farah@gmail.com","0987654321");
            CustomerEntity c3 = new CustomerEntity("Mai Do","mai@gmail.com","0456123789");

            customerRepo.save(c1);
            customerRepo.save(c2);
            customerRepo.save(c3);

        };
    }

}
