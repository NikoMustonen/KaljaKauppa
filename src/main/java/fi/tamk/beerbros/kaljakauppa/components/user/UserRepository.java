package fi.tamk.beerbros.kaljakauppa.components.user;

import org.springframework.data.repository.CrudRepository;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
public interface UserRepository
        extends CrudRepository<User, Integer> {
    
    public User findOneUserByUsernameAndPassword(String username, String password);
}
