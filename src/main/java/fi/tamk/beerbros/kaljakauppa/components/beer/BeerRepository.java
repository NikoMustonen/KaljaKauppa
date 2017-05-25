package fi.tamk.beerbros.kaljakauppa.components.beer;

import fi.tamk.beerbros.kaljakauppa.components.beertype.BeerType;
import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import fi.tamk.beerbros.kaljakauppa.components.manufacturer.Manufacturer;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Crud Repository interface for handling beer database table.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public interface BeerRepository extends CrudRepository<Beer, Integer> {

    /**
     * Finds all beers by country.
     *
     * @param country Country object.
     * @return List of beers.
     */
    public List<Beer> findByCountry(Country country);

    /**
     * Find all beers by beer type.
     *
     * @param beerType BeerType object.
     * @return List of beers.
     */
    public List<Beer> findByBeerType(BeerType beerType);

    /**
     * Find all beers by manufacturer.
     *
     * @param manufacturer Manufacturer object.
     * @return List of beers.
     */
    public List<Beer> findByManufacturer(Manufacturer manufacturer);

    /**
     * Find all beers by beer type and country.
     *
     * @param beerType BeerType object.
     * @param country Country object.
     * @return List of beers.
     */
    public List<Beer> findByBeerTypeAndCountry(
            BeerType beerType, Country country);

    /**
     * Find all beers by beer type and manufacturer.
     *
     * @param beerType BeerType object.
     * @param manufacturer Manufacturer object.
     * @return List of beers.
     */
    public List<Beer> findByBeerTypeAndManufacturer(
            BeerType beerType, Manufacturer manufacturer);

    /**
     * Find all beers by manufacturer and country.
     *
     * @param manufacturer Manufacturer object.
     * @param country Country object.
     * @return List of beers.
     */
    public List<Beer> findByCountryAndManufacturer(
            Country country, Manufacturer manufacturer);

    /**
     * Find all beers by beer type and country and manufacturer.
     *
     * @param manufacturer Manufacturer object.
     * @param beerType BeerType object.
     * @param country Country object.
     * @return List of beers.
     */
    public List<Beer> findByCountryAndManufacturerAndBeerType(
            Country country, Manufacturer manufacturer, BeerType beerType);
}
