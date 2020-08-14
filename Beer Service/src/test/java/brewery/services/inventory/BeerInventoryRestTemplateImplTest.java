package brewery.services.inventory;

import brewery.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerInventoryRestTemplateImplTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    @Autowired
    BeerRepository beerRepository;

    @BeforeEach
    void setUp(){

    }

    @Test
    void getOnHandInventory() {
        Integer qoh = beerInventoryService.getOnHandInventory(beerRepository.findAll().get(1).getId());

        System.out.println(qoh);
    }
}