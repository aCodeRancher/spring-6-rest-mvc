package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerMapper customerMapper;

    @Rollback
    @Transactional
    @Test
    void testListAllEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testListAll() {
        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void saveNewCustomerTest(){
        CustomerDTO customerDTO = CustomerDTO.builder()
                        .name("JT")
                        .createdDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now()).build();
       ResponseEntity responseEntity = customerController.handlePost(customerDTO);
       assertTrue(responseEntity.getStatusCode().equals(HttpStatusCode.valueOf(201)));
       assertTrue(responseEntity.getHeaders().getLocation()!=null);
       String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
       UUID savedID = UUID.fromString(location[4]);
       Customer customer = customerRepository.findById(savedID).get();
       assertTrue(customer.getName().equals("JT"));
    }

    @Test
    @Transactional
    @Rollback
    void updateCustomerByIdTest(){
       String diffNameStr = "diff name";
       Customer customerFound = customerRepository.findAll().get(0);
       UUID id = customerFound.getId();
       Customer  customerUpdate = Customer.builder().name(diffNameStr)
                                   .createdDate(LocalDateTime.now())
                                    .updateDate(LocalDateTime.now()).build();


       ResponseEntity responseEntity = customerController.updateCustomerByID(id, customerMapper.customerToCustomerDto(customerUpdate));
       assertTrue(responseEntity.getStatusCode().equals(HttpStatusCode.valueOf(204)));

       Optional<Customer> customer1 = customerRepository.findById(id) ;
        assertTrue(customer1.get().getName().equals(diffNameStr));
    }

    @Test
    void updateCustomerNotFound(){
        Customer customer = Customer.builder().name("NotFound")
                           .id(UUID.randomUUID()).build();
       assertThrows(NotFoundException.class, ()-> customerController.updateCustomerByID(customer.getId(),
                                                     customerMapper.customerToCustomerDto(customer)));
    }

    @Test
    @Transactional
    @Rollback
    void deleteCustomerById(){
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }

    @Test
    void deleteCustomerNotFound(){
        Customer customer = Customer.builder().id(UUID.randomUUID()).build();
        assertThrows(NotFoundException.class, ()-> customerController.deleteCustomerById(customer.getId()));
     }
}










