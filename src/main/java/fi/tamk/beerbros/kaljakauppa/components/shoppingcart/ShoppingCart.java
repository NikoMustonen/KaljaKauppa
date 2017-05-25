package fi.tamk.beerbros.kaljakauppa.components.shoppingcart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.*;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
@Entity(name = "shopping_cart")
public class ShoppingCart implements Serializable {
    
    @Id
    @Column(name = "user_id", unique = true)
    int id;
    
    @OneToMany(mappedBy = "shoppingCart")
    private Collection<ShoppingCartItem> items;
    
    public ShoppingCart(){}
    
    public ShoppingCart(int id){this.id = id;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(Collection<ShoppingCartItem> items) {
        this.items = items;
    }
    
    @JsonIgnore
    public ShoppingCartItem checkIfHasItemAndGetIt(ShoppingCartItem item) {
        for(ShoppingCartItem i : items) {
            if(Objects.equals(item.getBeer(), i.getBeer())) {
                return i;
            }
        }
        return null;
    }
}