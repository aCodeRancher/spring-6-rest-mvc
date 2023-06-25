package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BeerDataTest {

    @Autowired
    private  BeerRepository beerRepository;

    @Test
    void testGetBeers() throws  Exception{

        List<Beer> beerList = beerRepository.findAll();
        assertTrue(beerList.size()==2);
        Beer beer0 = beerList.get(0);
        assertTrue(beer0.getId()!=null);
        Beer beer1 = beerList.get(1);
        assertTrue(beer1.getId()!=null);
    }
}