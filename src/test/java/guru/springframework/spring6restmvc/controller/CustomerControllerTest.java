package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Test
    public void patchCustomerById() throws Exception{
        String nameToUpdate = "JT";
        String customers =  mockMvc.perform(get("/api/v1/customer"))
                          .andReturn().getResponse().getContentAsString();
        assertFalse(customers.contains(nameToUpdate));
        Customer[] customersArray = objectMapper.readValue(customers, Customer[].class);
        UUID id = customersArray[0].getId();
        Customer customerToPatch = Customer.builder().name("JT").build();
        String patchedCustomer = objectMapper.writeValueAsString(customerToPatch);
        mockMvc.perform(patch("/api/v1/customer/"+ id.toString()).contentType(MediaType.APPLICATION_JSON)
                .content(patchedCustomer)).andExpect(status().isNoContent());

        String updatedCustomerlist = mockMvc.perform(get("/api/v1/customer")).andReturn().getResponse().getContentAsString();
         assertTrue(updatedCustomerlist.contains(nameToUpdate));

    }
}
