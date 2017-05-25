package fi.tamk.beerbros.kaljakauppa.components.manufacturer;

import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
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
public interface ManufacturerRepository
        extends CrudRepository<Manufacturer, String> {

    @Query(value = "SELECT b FROM beer b WHERE b.manufacturer=:manufacturer")
    public Iterable<Beer> findAllBeerByManufacturer(@Param("manufacturer") Manufacturer manufacturer);
}
