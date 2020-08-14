package brewery.services;

import brewery.web.model.BeerDto;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<BeerDto> getAllBeers();

    BeerDto getById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
