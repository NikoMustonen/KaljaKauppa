package fi.tamk.beerbros.kaljakauppa.components.shoppingcart;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartRepository
        extends CrudRepository<ShoppingCart, Integer> {

    @Query("SELECT i FROM shopping_cart_item i WHERE i.shoppingCart=:id")
    public ShoppingCartItem findItem(@Param("id") Integer id);
}
