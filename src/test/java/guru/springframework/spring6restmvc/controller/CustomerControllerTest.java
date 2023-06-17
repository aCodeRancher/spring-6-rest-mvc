package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Test
    public void putCustomer() throws Exception{
       String allCustomers = mockMvc.perform(get("/api/v1/customer")).andReturn().getResponse().getContentAsString();
       Customer[] customers = objectMapper.readValue(allCustomers, Customer[].class);
       String id = customers[0].getId().toString();
       Customer updatedCustomer = Customer.builder().name("JT").build();
       String custString = objectMapper.writeValueAsString(updatedCustomer);
       mockMvc.perform(put("/api/v1/customer/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(custString)).andExpect(status().isNoContent());
      String  foundCustomer =  mockMvc.perform(get("/api/v1/customer/"+id)).andReturn().getResponse().getContentAsString();
      assertTrue(foundCustomer.contains("JT"));
    }
}
