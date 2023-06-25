package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@Slf4j
@AllArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception{
          loadBeers();
          loadCustomers();

    }

    private void loadBeers(){
        Beer beer0 = Beer.builder().beerName("Galaxy Cat")
                                    .beerStyle(BeerStyle.PALE_ALE)
                               .price(new BigDecimal("12.99"))
                                     .upc("12356")
                                    .quantityOnHand(122)
                                    .createdDate(LocalDateTime.now())
                                    .updateDate(LocalDateTime.now()).build();

        Beer beer1 = Beer.builder().beerName("Crank")
                     .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now()).build();

        beerRepository.save(beer0);
        beerRepository.save(beer1);
        log.debug("Number of beers: "+ beerRepository.count());
    }

    private void loadCustomers(){
        Customer customer0 = Customer.builder()
                            .name("Customer 1")
                            .createdDate(LocalDateTime.now())
                            .updateDate(LocalDateTime.now()).build();

        Customer customer1 = Customer.builder()
                .name("Customer 2")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now()).build();

        customerRepository.save(customer0);
        customerRepository.save(customer1);
        log.debug("Number of customers: "+ customerRepository.count());
    }
}
