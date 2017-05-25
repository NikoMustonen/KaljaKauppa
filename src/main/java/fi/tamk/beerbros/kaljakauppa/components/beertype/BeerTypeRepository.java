package fi.tamk.beerbros.kaljakauppa.components.beertype;

import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
public interface BeerTypeRepository extends CrudRepository<BeerType, String>{
    
    @Query(value = "SELECT DISTINCT p FROM beer b LEFT JOIN b.country p WHERE b.beerType=:beerType")
    public Iterable<Country> findAllCountriesByBeerType(@Param("beerType") BeerType beerType);
    
    @Query(value = "SELECT DISTINCT p FROM beer b LEFT JOIN b.country p WHERE b.country=:country AND b.beerType=:beerType")
    public Country findCountryByCountryAndType (
            @Param("country") Country country,
            @Param("beerType") BeerType beerType);
    
    @Query(value = "SELECT b FROM beer b WHERE b.beerType=:beerType AND b.country=:country")
    public Iterable<Beer> findAllBeerByBeerTypeAndCountry(
            @Param("beerType") BeerType beerType,
            @Param("country") Country country);
}
