package fi.tamk.beerbros.kaljakauppa.components.shoppingcart;

import fi.tamk.beerbros.kaljakauppa.components.user.User;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class for order entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Entity
@Table(name = "orderr")
public class Order {

    /**
     * Order id number.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    Long id;

    /**
     * Users associated with current order.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    protected User user;

    /**
     * Items in current order.
     */
    @OneToMany(mappedBy = "order")
    private Collection<ShoppingCartItem> items;

    /**
     * Creates empty order.
     */
    public Order() {
    }

    /**
     * Gets order id.
     *
     * @return Id number.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id for the order.
     *
     * @param id Id number.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns user associated with current order.
     *
     * @return User entity object.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user associated with current order.
     *
     * @param user User entity object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets items from order.
     *
     * @return List of items.
     */
    public Collection<ShoppingCartItem> getItems() {
        return items;
    }

    /**
     * Sets items for order.
     *
     * @param items List of items.
     */
    public void setItems(Collection<ShoppingCartItem> items) {
        this.items = items;
    }
}
