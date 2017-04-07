package fi.tamk.beerbros.kaljakauppa.components.product.beer;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class BeerService {
    
    @Autowired
    private BeerRepository beerRepository;
    
    public Iterable<Beer> getAllBeers() {
        return beerRepository.findAll();
    }
    
    public Iterable<Beer> getAllBeersByCountry(String name) {
        return beerRepository.findBeersByCountry(name);
    }
    
    public ResponseEntity<?> addBeer(Beer beer) {
        try {
            beerRepository.save(beer);
            URI path = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(beer.getId()).toUri();
            return ResponseEntity.created(path).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
