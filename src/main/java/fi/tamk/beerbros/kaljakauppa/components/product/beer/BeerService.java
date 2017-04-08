package fi.tamk.beerbros.kaljakauppa.components.product.beer;

import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import fi.tamk.beerbros.kaljakauppa.components.type.Type;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class BeerService {
    
    @Autowired
    private BeerRepository br;
    
    public Iterable<Beer> getAllBeers() {
        return br.findAll();
    }
    
    public Iterable<Beer> getAllBeersByCountry(String name) {
        return br.findBeerByCountry(new Country(name, ""));
    }
    
    public ResponseEntity<?> getBeerById(int id) {
        try {
            return ResponseEntity.ok(br.findOne(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    public ResponseEntity<?> getBeerByCountryAndId(String name ,Integer id) {
        try {
            return ResponseEntity.ok(br.findBeerByCountryAndId(new Country(name, null), id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    public ResponseEntity<?> getBeersByType(String type) {
        try {
            return ResponseEntity.ok(br.findBeersByType(new Type(type, null)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    public ResponseEntity<?> getBeerByTypeAndId(String type, Integer id) {
        try {
            return ResponseEntity.ok(br.findBeerByTypeAndId(new Type(type, null), id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    public ResponseEntity<?> getBeersByTypeAndCountry(String type, String country) {
        try {
            return ResponseEntity.ok(br.findBeersByTypeAndCountry(new Type(type, null), new Country(country, null)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    public ResponseEntity<?> getBeerByTypeAndCountryAndId(String type, String country, Integer id) {
        try {
            return ResponseEntity.ok(br.findBeerByTypeAndCountryAndId(new Type(type, null), new Country(country, null), id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    public ResponseEntity<?> addBeer(Beer beer) {
        try {
            br.save(beer);
            URI path = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(beer.getId()).toUri();
            return ResponseEntity.created(path).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
