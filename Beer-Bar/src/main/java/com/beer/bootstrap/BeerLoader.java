package com.beer.bootstrap;

import com.beer.domain.Beer;
import com.beer.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    @Autowired
    private BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {

        if(beerRepository.count() == 0){
            beerRepository.save(Beer.builder()
                    .beerName("Corona")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .upc(337010000001L)
                    .minOnHand(12)
                    .price(new BigDecimal("12.85"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Heineken")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .upc(337010000002L)
                    .minOnHand(12)
                    .price(new BigDecimal("09.85"))
                    .build());
        }
    }
}
