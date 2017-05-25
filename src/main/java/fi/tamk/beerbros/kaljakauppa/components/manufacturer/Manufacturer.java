package fi.tamk.beerbros.kaljakauppa.components.manufacturer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

/**
 * Entity class for manufacturer entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Entity(name = "manufacturer")
public class Manufacturer implements Serializable {
    
    /**
     * Manufacturer id number.
     */
    @Id
    @Column(name = "name", nullable = true, unique = true)
    private String name;
    
    /**
     * Manufacturer description text string.
     */
    @Column(name = "description", nullable = true)
    private String description;
    
    /**
     * All beers under this manufacturer.
     */
    @OneToMany(mappedBy = "manufacturer")
    @JsonIgnore
    private Collection<Beer> products;
    
    /**
     * Constructs empty manufacturer object.
     */
    public Manufacturer() {}
    
    /**
     * Constructs manufacturer by given name.
     * 
     * Is used for fetching data from the database.
     * 
     * @param name Name id String.
     */
    public Manufacturer(String name) {this.name = name;}

    /**
     * Gets manufacturers name.
     * 
     * @return Name String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets manufacturer name.
     * 
     * @param name Name String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets manufacturer description string.
     * 
     * @return Description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets manufacturers description.
     * 
     * @param description Description text string.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets products under this manufacturer.
     * 
     * @return List of beers.
     */
    public Collection<Beer> getProducts() {
        return products;
    }

    /**
     * Sets products under this manufacturer.
     * 
     * @param products List of beers.
     */
    public void setProducts(Collection<Beer> products) {
        this.products = products;
    }
}
