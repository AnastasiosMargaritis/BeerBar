package brewery.services;

import brewery.domain.Beer;
import brewery.repositories.BeerRepository;
import brewery.web.mappers.BeerMapper;
import brewery.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import brewery.web.controllers.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDto> getAllBeers() {

        List<Beer> beers = beerRepository.findAll();
        List<BeerDto> beerDtos = new ArrayList<>();

        for (Beer beer: beers){
            beerDtos.add(beerMapper.beerToBeerDto(beer));
        }
        return beerDtos;
    }

    @Override
    public BeerDto getById(UUID beerId) {

            return beerMapper.beerToBeerDto(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
            );
    }


    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

}
