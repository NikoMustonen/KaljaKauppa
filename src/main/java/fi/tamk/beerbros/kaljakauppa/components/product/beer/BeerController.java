package fi.tamk.beerbros.kaljakauppa.components.product.beer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class BeerController {

    @Autowired
    BeerService bs;
    
    //GETTERS
    @RequestMapping("/beers")
    public Iterable<Beer> getAllBeers() {
        return bs.getAllBeers();
    }

    @RequestMapping("/beers/{id}")
    public ResponseEntity<?> getBeerById(@PathVariable("id") int id) {
        return bs.getBeerById(id);
    }

    @RequestMapping("/countries/{country}/beers")
    public Iterable<?> getAllBeersByCountry(@PathVariable String country) {
        return bs.getAllBeersByCountry(country);
    }

    @RequestMapping("/countries/{country}/beers/{id}")
    public ResponseEntity<?> getAllBeersByCountryAndId(
            @PathVariable("country") String country,
            @PathVariable("id") Integer id) {
        return bs.getBeerByCountryAndId(country, id);
    }

    @RequestMapping("/beertypes/{type}/beers")
    public ResponseEntity<?> getAllBeersByType(
            @PathVariable("type") String type) {
        return bs.getBeersByType(type);
    }

    @RequestMapping("/beertypes/{type}/beers/{id}")
    public ResponseEntity<?> getBeerByTypeAndId(
            @PathVariable("type") String type, 
            @PathVariable("id") Integer id) {
        return bs.getBeerByTypeAndId(type, id);
    }

    @RequestMapping("/countries/{country}/beertypes/{type}/beers")
    public ResponseEntity<?> getBeerByTypeAndCountry(
            @PathVariable("type") String type, 
            @PathVariable("country") String country) {
        return bs.getBeersByTypeAndCountry(type, country);
    }

    @RequestMapping("/countries/{country}/beertypes/{type}/beers/{id}")
    public ResponseEntity<?> getBeerByTypeAndCountryAndId(
            @PathVariable("type") String type, 
            @PathVariable("country") String country, 
            @PathVariable("id") Integer id) {
        return bs.getBeerByTypeAndCountryAndId(type, country, id);
    }

    @RequestMapping("/beertypes/{type}/countries/{country}/beers")
    public ResponseEntity<?> getBeerByCountryAndType(
            @PathVariable("type") String type, 
            @PathVariable("country") String country) {
        return bs.getBeersByTypeAndCountry(type, country);
    }

    @RequestMapping("/beertypes/{type}/countries/{country}/beers/{id}")
    public ResponseEntity<?> getBeerByCountryAndType(
            @PathVariable("type") String type, 
            @PathVariable("country") String country, 
            @PathVariable("id") Integer id) {
        return bs.getBeerByTypeAndCountryAndId(type, country, id);
    }

    @RequestMapping(value = "/beers", method = RequestMethod.POST)
    public ResponseEntity<?> addBeer(@RequestBody Beer beer) {
        return bs.addBeer(beer);
    }
}
