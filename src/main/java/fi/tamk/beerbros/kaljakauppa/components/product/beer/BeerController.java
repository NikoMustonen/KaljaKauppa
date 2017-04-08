package fi.tamk.beerbros.kaljakauppa.components.product.beer;

import fi.tamk.beerbros.kaljakauppa.components.MainComponent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class BeerController extends MainComponent {

    //GETTERS
    @RequestMapping("/beers")
    public Iterable<Beer> getAllBeers() {
        return beerService.getAllBeers();
    }

    @RequestMapping("/beers/{id}")
    public ResponseEntity<?> getBeerById(@PathVariable("id") int id) {
        return beerService.getBeerById(id);
    }

    @RequestMapping("/countries/{country}/beers")
    public Iterable<?> getAllBeersByCountry(@PathVariable String country) {
        return beerService.getAllBeersByCountry(country);
    }

    @RequestMapping("/countries/{country}/beers/{id}")
    public ResponseEntity<?> getAllBeersByCountryAndId(
            @PathVariable("country") String country,
            @PathVariable("id") Integer id) {
        return beerService.getBeerByCountryAndId(country, id);
    }

    @RequestMapping("/beertypes/{type}/beers")
    public ResponseEntity<?> getAllBeersByType(
            @PathVariable("type") String type) {
        return beerService.getBeersByType(type);
    }

    @RequestMapping("/beertypes/{type}/beers/{id}")
    public ResponseEntity<?> getBeerByTypeAndId(
            @PathVariable("type") String type, 
            @PathVariable("id") Integer id) {
        return beerService.getBeerByTypeAndId(type, id);
    }

    @RequestMapping("/countries/{country}/beertypes/{type}/beers")
    public ResponseEntity<?> getBeerByTypeAndCountry(
            @PathVariable("type") String type, 
            @PathVariable("country") String country) {
        return beerService.getBeersByTypeAndCountry(type, country);
    }

    @RequestMapping("/countries/{country}/beertypes/{type}/beers/{id}")
    public ResponseEntity<?> getBeerByTypeAndCountryAndId(
            @PathVariable("type") String type, 
            @PathVariable("country") String country, 
            @PathVariable("id") Integer id) {
        return beerService.getBeerByTypeAndCountryAndId(type, country, id);
    }

    @RequestMapping("/beertypes/{type}/countries/{country}/beers")
    public ResponseEntity<?> getBeerByCountryAndType(
            @PathVariable("type") String type, 
            @PathVariable("country") String country) {
        return beerService.getBeersByTypeAndCountry(type, country);
    }

    @RequestMapping("/beertypes/{type}/countries/{country}/beers/{id}")
    public ResponseEntity<?> getBeerByCountryAndType(
            @PathVariable("type") String type, 
            @PathVariable("country") String country, 
            @PathVariable("id") Integer id) {
        return beerService.getBeerByTypeAndCountryAndId(type, country, id);
    }

    @RequestMapping(value = "/beers", method = RequestMethod.POST)
    public ResponseEntity<?> addBeer(@RequestBody Beer beer) {
        return beerService.addBeer(beer);
    }
}
