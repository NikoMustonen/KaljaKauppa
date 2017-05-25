package fi.tamk.beerbros.kaljakauppa.components.shoppingcart;

import org.springframework.data.repository.CrudRepository;

/**
 * Database handler for shopping items.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public interface ShoppingItemRepository
        extends CrudRepository<ShoppingCartItem, Long> {
}
