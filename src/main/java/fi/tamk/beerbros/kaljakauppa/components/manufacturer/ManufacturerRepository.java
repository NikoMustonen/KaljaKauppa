package fi.tamk.beerbros.kaljakauppa.components.manufacturer;

import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Database handler for manufacturer entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public interface ManufacturerRepository
        extends CrudRepository<Manufacturer, String> {

    /**
     * Finds all beers by given manufacturer.
     *
     * @param manufacturer Given Manufacturer entity object.
     * @return
     */
    @Query(value = "SELECT b FROM beer b WHERE b.manufacturer=:manufacturer")
    public Iterable<Beer> findAllBeerByManufacturer(
            @Param("manufacturer") Manufacturer manufacturer);
}
