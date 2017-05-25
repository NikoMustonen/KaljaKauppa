package fi.tamk.beerbros.kaljakauppa.components.review;

import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import fi.tamk.beerbros.kaljakauppa.components.user.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Database handler for review entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public interface ReviewRepository
        extends CrudRepository<Review, Long> {

    /**
     * Finds review by user and by beer.
     *
     * Is used for checking duplicates.
     *
     * @param u User entity object.
     * @param b Beer entity object.
     * @return Review entity object.
     */
    public Review findOneByUserAndBeer(User u, Beer b);
}
