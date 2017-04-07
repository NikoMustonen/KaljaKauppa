package fi.tamk.beerbros.kaljakauppa.components.product.beer;

import org.springframework.data.repository.CrudRepository;

public interface BeerRepository extends CrudRepository<Beer, Integer> {
    
    public Iterable<Beer> findBeersByCountry(String name);
}
