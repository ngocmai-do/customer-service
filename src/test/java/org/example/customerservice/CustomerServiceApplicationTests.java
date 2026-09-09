package org.example.customerservice;

import org.example.customerservice.entity.CustomerEntity;
import org.example.customerservice.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerServiceApplicationTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void createCustomerReturn201() throws Exception {

        String customerJson = """
                {
                    "name": "Anna Andersson",
                    "email": "anna@test.se",
                    "tel": "0701234567"
                }
                """;

        mvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Anna Andersson"))
                .andExpect(jsonPath("$.email").value("anna@test.se"))
                .andExpect(jsonPath("$.tel").value("0701234567"));
    }

    @Test
    void createCustomerInvalidEmailReturn400() throws Exception {

        String customerJson = """
                {
                    "name": "Anna Andersson",
                    "email": "felaktig-email",
                    "tel": "0701234567"
                }
                """;

        mvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllCustomersReturn200AndCustomers() throws Exception {

        customerRepository.save(
                new CustomerEntity(
                        "Anna Andersson",
                        "anna@test.se",
                        "0701234567"
                )
        );

        customerRepository.save(
                new CustomerEntity(
                        "Erik Eriksson",
                        "erik@test.se",
                        "0709876543"
                )
        );

        mvc.perform(get("/customers/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Anna Andersson"))
                .andExpect(jsonPath("$[1].name").value("Erik Eriksson"));
    }
}
