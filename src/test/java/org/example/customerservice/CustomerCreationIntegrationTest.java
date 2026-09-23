package org.example.customerservice;

import org.example.customerservice.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerCreationIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void createCustomer_persistsToDatabase_andReturns201() throws Exception {
        String customerJson = """
                {
                    "name": "Mai",
                    "email": "mai@test.se",
                    "tel": "0701234567"
                }
                """;

        mvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Mai"))
                .andExpect(jsonPath("$.email").value("mai@test.se"))
                .andExpect(jsonPath("$.tel").value("0701234567"));

        // Confirm it actually landed in the DB, not just the HTTP response
        assert customerRepository.findAll().size() == 1;
        assert customerRepository.findAll().get(0).getEmail().equals("mai@test.se");
    }

    @Test
    void createCustomer_invalidEmail_returns400() throws Exception {
        String customerJson = """
                {
                    "name": "Maiiii",
                    "email": "not-an-email",
                    "tel": "0701234567"
                }
                """;

        mvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isBadRequest());

        // Nothing should have been persisted on a validation failure
        assert customerRepository.findAll().isEmpty();
    }

}
