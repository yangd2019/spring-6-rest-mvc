package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerServiceJPATest {

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerMapper beerMapper;
    BeerService beerService;

    @BeforeEach
    void setUp() {
        beerService = new BeerServiceJPA(beerRepository,beerMapper);
    }

    @Test
    void listBeers() {
        List<BeerDTO> beerlist = beerService.listBeers(null,null,null,null,null).getContent();
        System.out.println("beerlist.size()= "+beerlist.size());
        assertNotEquals(0,beerlist.size());
    }

    @Test
    void getBeerById() {
    }

    @Test
    void saveNewBeer() {
    }

    @Test
    void updateBeerById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void patchBeerById() {
    }
}