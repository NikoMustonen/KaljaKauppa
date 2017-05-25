package fi.tamk.beerbros.kaljakauppa.components.shoppingcart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.*;

/**
 * Entity class for shopping cart entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Entity(name = "shopping_cart")
public class ShoppingCart implements Serializable {

    /**
     * Shopping cart and user id.
     */
    @Id
    @Column(name = "user_id", unique = true)
    int id;

    /**
     * Items in the shopping cart.
     */
    @OneToMany(mappedBy = "shoppingCart")
    private Collection<ShoppingCartItem> items;

    /**
     * Generates empty shopping cart.
     */
    public ShoppingCart() {}

    /**
     * Generates shopping cart with given id.
     * 
     * Is used for fetching shopping carts from the database.
     * 
     * @param id Id number.
     */
    public ShoppingCart(int id) {
        this.id = id;
    }

    /**
     * Gets shopping carts id.
     * 
     * @return Id number.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets shopping cart id.
     * 
     * @param id Id number.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns shopping cart items.
     * 
     * @return List of items.
     */
    public Collection<ShoppingCartItem> getItems() {
        return items;
    }

    /**
     * Sets shopping cart items.
     * 
     * @param items List of items.
     */
    public void setItems(Collection<ShoppingCartItem> items) {
        this.items = items;
    }

    /**
     * Checks whether cart already has given sort of item.
     * 
     * If there is already is item like given item the item will be returned.
     * 
     * @param item Checked item.
     * @return Null if not found.
     */
    @JsonIgnore
    public ShoppingCartItem checkIfHasItemAndGetIt(ShoppingCartItem item) {
        for (ShoppingCartItem i : items) {
            if (Objects.equals(item.getBeer(), i.getBeer())) {
                return i;
            }
        }
        return null;
    }
}
