package brewery.services.beer;

import brewery.web.model.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Service
public class BeerServiceImpl implements BeerService {

    private final String BEER_PATH_V1 = "/api/v1/beer/";
    private final String BEER_UPC_PATH_v1 = "/api/v1/beerUpc/";
    private final RestTemplate restTemplate;

    private String beerServiceHost;

    public BeerServiceImpl(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID id) {
        return Optional.of(restTemplate.getForObject(
                beerServiceHost + BEER_PATH_V1 + id.toString(), BeerDto.class
        ));
    }

    @Override
    public Optional<BeerDto> getBeerByUpc(String upc) {
        return Optional.of(restTemplate.getForObject(
                beerServiceHost + BEER_UPC_PATH_v1 + upc, BeerDto.class
        ));
    }

    public void setBeerServiceHost(String beerServiceHost) {
        this.beerServiceHost = beerServiceHost;
    }
}
