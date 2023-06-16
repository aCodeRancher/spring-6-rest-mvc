package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class CusomterControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getCustomers() throws Exception{

     String response =  mockMvc.perform(get("/api/v1/customer"))
                        .andReturn().getResponse().getContentAsString();
     Customer[] customers = objectMapper.readValue(response, Customer[].class);
     assertTrue(customers.length==3);
     }

     @Test
    void getCustomerById() throws Exception{
         UUID aUUID = UUID.fromString("c414fd04-af16-4f4d-afd9-11e8604b4df3");
         String response =  mockMvc.perform(get("/api/v1/customer/"+aUUID.toString()))
                 .andReturn().getResponse().getContentAsString();
         Customer customer = objectMapper.readValue(response, Customer.class);
         assertTrue(customer.getCustomerName().equals("John Thompson"));
     }
}
