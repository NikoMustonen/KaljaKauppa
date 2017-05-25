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

@Entity(name = "shopping_cart_item")
public class ShoppingCartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "shopping_cart", referencedColumnName = "user_id", nullable = true)
    private ShoppingCart shoppingCart;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = true)
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "beer", referencedColumnName = "id", nullable = false)
    private Beer beer;
    
    @Column(name = "amount", nullable = true)
    private Integer amount;
    
    @JsonIgnore
    private boolean isShoppingCartVisible = true;
    
    public ShoppingCartItem() {}
    
    public ShoppingCartItem(Long id) {this.id = id;}

    //@JsonIgnore
    public Long getId() {
        return id;
    }

    //@JsonIgnore
    public void setId(Long id) {
        this.id = 0L;
    }

    @JsonIgnore
    public void setShoppingCartVisible(boolean isVisible) {
        this.isShoppingCartVisible = isVisible;
    }
    
    public ShoppingCart getShoppingCart() {
        if(isShoppingCartVisible) {
            return shoppingCart;
        } else return null;
    }

    public void setShoppingCart(Integer shoppingCartId) {
        if(shoppingCartId != null){
            ShoppingCart sc = new ShoppingCart(shoppingCartId);
            this.shoppingCart = sc;
        } else {
            this.shoppingCart = null;
        }
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getBeer() {
        return beer.getId();
    }

    public void setBeer(Integer beerId) {
        Beer b = new Beer(beerId);
        this.beer = b;
    }

    @JsonIgnore
    public Order getOrder() {
        return order;
    }

    @JsonIgnore
    public void setOrder(Order order) {
        this.order = order;
    }
}
