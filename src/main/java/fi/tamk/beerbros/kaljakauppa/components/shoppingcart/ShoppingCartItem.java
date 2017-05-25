package fi.tamk.beerbros.kaljakauppa.components.shoppingcart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
@Entity(name = "shopping_cart_item")
public class ShoppingCartItem {

    /**
     * Item id number.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Items shopping cart.
     *
     * If null, then this order is already placed.
     */
    @ManyToOne
    @JoinColumn(name = "shopping_cart", referencedColumnName = "user_id", nullable = true)
    private ShoppingCart shoppingCart;

    /**
     * Items order.
     *
     * If null, then this is still in shopping cart.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = true)
    private Order order;

    /**
     * Beer associated to this shopping cart item.
     */
    @ManyToOne
    @JoinColumn(name = "beer", referencedColumnName = "id", nullable = false)
    private Beer beer;

    /**
     * Amount of items.
     */
    @Column(name = "amount", nullable = true)
    private Integer amount;

    /**
     * Defines whether shopping cart is visible for JSON response.
     */
    @JsonIgnore
    private boolean isShoppingCartVisible = true;

    /**
     * Generates empty item.
     */
    public ShoppingCartItem() {
    }

    /**
     * Generates item with given id.
     *
     * Is used to fetch data from item database table.
     *
     * @param id Item id number.
     */
    public ShoppingCartItem(Long id) {
        this.id = id;
    }

    /**
     * Gets item id number.
     *
     * @return Id Long value.
     */
    //@JsonIgnore
    public Long getId() {
        return id;
    }

    /**
     * Sets id for the item.
     *
     * @param id Id long value.
     */
    //@JsonIgnore
    public void setId(Long id) {
        this.id = 0L;
    }

    /**
     * Sets shopping carts visibility for this item.
     *
     * Is used to hide shopping cart element form the JSON output.
     *
     * @param isVisible Whether cart is visible in current item.
     */
    @JsonIgnore
    public void setShoppingCartVisible(boolean isVisible) {
        this.isShoppingCartVisible = isVisible;
    }

    /**
     * Returns items associated shopping cart.
     *
     * @return ShoppingCart object.
     */
    public ShoppingCart getShoppingCart() {
        if (isShoppingCartVisible) {
            return shoppingCart;
        } else {
            return null;
        }
    }

    /**
     * Sets shopping cart for the item.
     *
     * @param shoppingCartId Shopping cart Id Integer.
     */
    public void setShoppingCart(Integer shoppingCartId) {
        if (shoppingCartId != null) {
            ShoppingCart sc = new ShoppingCart(shoppingCartId);
            this.shoppingCart = sc;
        } else {
            this.shoppingCart = null;
        }
    }

    /**
     * Returns the amount of current product.
     *
     * @return Amount Integer.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the amount of current product.
     *
     * @param amount Amount integer.
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Returns the beer associated to this item.
     *
     * @return Beer object.
     */
    public Integer getBeer() {
        return beer.getId();
    }

    /**
     * Sets beer for this item.
     * 
     * @param beerId Beer id Integer.
     */
    public void setBeer(Integer beerId) {
        Beer b = new Beer(beerId);
        this.beer = b;
    }

    /**
     * Returns current order for this item.
     * 
     * Returns null if item is still in shopping cart.
     * 
     * @return Order object.
     */
    @JsonIgnore
    public Order getOrder() {
        return order;
    }

    /**
     * Sets order for item when order is placed.
     * 
     * @param order Order object.
     */
    @JsonIgnore
    public void setOrder(Order order) {
        this.order = order;
    }
}
