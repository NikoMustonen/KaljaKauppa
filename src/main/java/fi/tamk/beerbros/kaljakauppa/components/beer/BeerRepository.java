package fi.tamk.beerbros.kaljakauppa.components.beer;

import fi.tamk.beerbros.kaljakauppa.components.beertype.BeerType;
import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface BeerRepository extends CrudRepository<Beer, Integer>{
    
    public List<Beer> findByCountry(Country country);
    
    public List<Beer> findByBeerType(BeerType beerType);
}
