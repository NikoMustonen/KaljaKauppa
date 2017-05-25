package fi.tamk.beerbros.kaljakauppa.components.country;

import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import fi.tamk.beerbros.kaljakauppa.components.beertype.BeerType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Crud repository for country entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public interface CountryRepository extends CrudRepository<Country, String>{
    
    /**
     * Finds all Beer Types By Country
     * 
     * @param country Country entity object.
     * @return 
     */
    @Query(value = "SELECT DISTINCT p FROM beer b LEFT JOIN b.beerType p "
            + "WHERE b.country=:country")
    public Iterable<BeerType> findAllBeerTypesByCountryId(
            @Param("country") Country country);
    
    /**
     * Finds all beer types that have beer in given country.
     * 
     * @param country Given country.
     * @param beerType Given beer type.
     * @return 
     */
    @Query(value = "SELECT DISTINCT p FROM beer b LEFT JOIN b.beerType p "
            + "WHERE b.country=:country AND b.beerType=:beerType")
    public BeerType findBeerTypeByCountryAndType (
            @Param("country") Country country,
            @Param("beerType") BeerType beerType);
    
    /**
     * Finds all beers by beer type and country.
     * 
     * @param beerType BeerType entity object.
     * @param country Country entity object.
     * @return Beer entity Object.
     */
    @Query(value = "SELECT b FROM beer b WHERE b.beerType=:beerType "
            + "AND b.country=:country")
    public Iterable<Beer> findAllBeerByBeerTypeAndCountry(
            @Param("beerType") BeerType beerType,
            @Param("country") Country country);
}
