package fi.tamk.beerbros.kaljakauppa.components.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository
        extends CrudRepository<User, Integer> {
}
