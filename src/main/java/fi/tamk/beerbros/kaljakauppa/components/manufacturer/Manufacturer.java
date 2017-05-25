package fi.tamk.beerbros.kaljakauppa.components.manufacturer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
@Entity(name = "manufacturer")
public class Manufacturer implements Serializable {
    
    @Id
    @Column(name = "name", nullable = true, unique = true)
    private String name;
    
    @Column(name = "description", nullable = true)
    private String description;
    
    @OneToMany(mappedBy = "manufacturer")
    @JsonIgnore
    private Collection<Beer> products;
    
    public Manufacturer() {}
    
    public Manufacturer(String name) {this.name = name;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Beer> getProducts() {
        return products;
    }

    public void setProducts(Collection<Beer> products) {
        this.products = products;
    }
}
