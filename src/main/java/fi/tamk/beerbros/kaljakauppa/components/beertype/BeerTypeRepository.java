package fi.tamk.beerbros.kaljakauppa.components.beertype;

import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Crud repository for BeerType database table.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public interface BeerTypeRepository extends CrudRepository<BeerType, String> {

    /**
     * Selects all distinct countries from the database which have chosen beer
     * type.
     *
     * @param beerType Chosen beer type.
     * @return List of countries.
     */
    @Query(value = "SELECT DISTINCT p FROM beer b LEFT JOIN b.country p "
            + "WHERE b.beerType=:beerType")
    public Iterable<Country> findAllCountriesByBeerType(
            @Param("beerType") BeerType beerType);

    /**
     * Selects all Countries from database with given beer type.
     *
     * I have no idea what i was thinking here.
     *
     * @param country Country object.
     * @param beerType BeerTypeObject.
     * @return Specific country entity object.
     */
    @Query(value = "SELECT DISTINCT p FROM beer b LEFT JOIN b.country p "
            + "WHERE b.country=:country AND b.beerType=:beerType")
    public Country findCountryByCountryAndType(
            @Param("country") Country country,
            @Param("beerType") BeerType beerType);

    /**
     * Selects all beers from database with given beer type and country.
     *
     * Again... I have no idea why this is here. I am just too afraid to remove
     * these methods.
     *
     * @param beerType BeerType object.
     * @param country Country object.
     * @return List of beers.
     */
    @Query(value = "SELECT b FROM beer b WHERE b.beerType=:beerType "
            + "AND b.country=:country")
    public Iterable<Beer> findAllBeerByBeerTypeAndCountry(
            @Param("beerType") BeerType beerType,
            @Param("country") Country country);
}
