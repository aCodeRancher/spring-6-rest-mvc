package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void deleteCustomerById() throws Exception{
       String customers =  mockMvc.perform(get("/api/v1/customer"))
                           .andReturn().getResponse().getContentAsString();
       Customer[] customersArray = objectMapper.readValue(customers, Customer[].class);
       UUID idToRemove = customersArray[0].getId();

       mockMvc.perform(delete("/api/v1/customer/"+ idToRemove.toString()))
               .andExpect(status().isNoContent());

       String customersUpdated = mockMvc.perform(get("/api/v1/customer"))
                .andReturn().getResponse().getContentAsString();
       assertFalse(customersUpdated.contains(idToRemove.toString()));
    }
}
