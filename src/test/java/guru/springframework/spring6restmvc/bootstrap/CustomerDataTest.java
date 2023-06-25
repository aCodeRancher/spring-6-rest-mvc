package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CustomerDataTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void getCustomersTest() throws Exception{

        List<Customer> customerList =customerRepository.findAll();
        assertTrue(customerList.size()==2);
        Customer customer0 = customerList.get(0);
        assertTrue(customer0.getId()!=null);
        Customer customer1 = customerList.get(1);
        assertTrue(customer1.getId()!=null);
    }
}
