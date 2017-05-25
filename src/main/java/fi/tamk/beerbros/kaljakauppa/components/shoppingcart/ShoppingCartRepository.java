package fi.tamk.beerbros.kaljakauppa.components.shoppingcart;

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
public interface ShoppingCartRepository
        extends CrudRepository<ShoppingCart, Integer> {

    @Query("SELECT i FROM shopping_cart_item i WHERE i.shoppingCart=:id")
    public ShoppingCartItem findItem(@Param("id") Integer id);
}
