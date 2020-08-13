package com.beer.bootstrap;

import com.beer.domain.Beer;
import com.beer.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0617648573629";
    public static final String BEER_3_UPC = "0687415966366";

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
                    .upc(BEER_1_UPC)
                    .minOnHand(12)
                    .price(new BigDecimal("12.85"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Heineken")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .upc(BEER_2_UPC)
                    .minOnHand(12)
                    .price(new BigDecimal("09.85"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Kaiser")
                    .beerStyle("SAISON")
                    .quantityToBrew(170)
                    .upc(BEER_3_UPC)
                    .minOnHand(20)
                    .price(new BigDecimal("17.11"))
                    .build());
        }
    }
}
