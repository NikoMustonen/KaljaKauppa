package fi.tamk.beerbros.kaljakauppa.components.shoppingcart;

import org.springframework.data.repository.CrudRepository;

/**
 * CRUD repository class for orders.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
}
