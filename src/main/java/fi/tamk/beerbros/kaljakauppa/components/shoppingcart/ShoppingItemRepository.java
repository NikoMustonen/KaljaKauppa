package fi.tamk.beerbros.kaljakauppa.components.shoppingcart;

import org.springframework.data.repository.CrudRepository;

public interface ShoppingItemRepository extends CrudRepository<ShoppingCartItem, Long> {
}
