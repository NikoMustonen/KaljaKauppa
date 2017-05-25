package fi.tamk.beerbros.kaljakauppa.components.shoppingcart;

import org.springframework.data.repository.CrudRepository;

/**
 * Database handler for shoppingCarts.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public interface ShoppingCartRepository
        extends CrudRepository<ShoppingCart, Integer> {
}
