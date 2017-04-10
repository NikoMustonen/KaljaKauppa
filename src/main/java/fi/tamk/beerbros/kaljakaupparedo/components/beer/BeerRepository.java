package fi.tamk.beerbros.kaljakaupparedo.components.beer;

import fi.tamk.beerbros.kaljakaupparedo.components.beertype.BeerType;
import fi.tamk.beerbros.kaljakaupparedo.components.country.Country;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface BeerRepository extends CrudRepository<Beer, Integer>{
    
    public List<Beer> findByCountry(Country country);
    
    public List<Beer> findByBeerType(BeerType beerType);
}
