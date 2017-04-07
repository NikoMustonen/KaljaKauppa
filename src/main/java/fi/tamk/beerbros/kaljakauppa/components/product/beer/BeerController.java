package fi.tamk.beerbros.kaljakauppa.components.product.beer;

import fi.tamk.beerbros.kaljakauppa.components.MainComponent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class BeerController extends MainComponent{

    @RequestMapping("/beers")
    public Iterable<Beer> getAllBeers() {
        return beerService.getAllBeers();
    }
    
    @RequestMapping("/countries/{country}/beers")
    public Iterable<Beer> getAllBeersByCountry(@PathVariable String country) {
        return beerService.getAllBeers();
    }
    
    @RequestMapping(value = "/beers", method = RequestMethod.POST)
    public ResponseEntity<?> addBeer(@RequestBody Beer beer) {
        return beerService.addBeer(beer);
    }
    
    /*@RequestMapping(value = "/beers", method = RequestMethod.POST)
    public ResponseEntity<?> modifyBeer(@RequestBody Beer beer) {
        return beerService.addBeer(beer);
    }*/
}
