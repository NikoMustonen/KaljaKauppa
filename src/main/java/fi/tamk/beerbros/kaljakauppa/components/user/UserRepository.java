package fi.tamk.beerbros.kaljakauppa.components.user;

import org.springframework.data.repository.CrudRepository;

/**
 * Crud repository for user entity objects.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public interface UserRepository
        extends CrudRepository<User, Integer> {

    /**
     * Returns user with given username and password.
     *
     * @param username Username String.
     * @param password Password String.
     * @return User entity object.
     */
    public User findOneUserByUsernameAndPassword(
            String username, String password);
}
