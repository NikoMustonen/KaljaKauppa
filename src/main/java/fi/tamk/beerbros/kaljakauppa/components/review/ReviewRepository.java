package fi.tamk.beerbros.kaljakauppa.components.review;

import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import fi.tamk.beerbros.kaljakauppa.components.user.User;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository
        extends CrudRepository<Review, Long> {
    
    public Review findOneByUserAndBeer(User u, Beer b);
}
