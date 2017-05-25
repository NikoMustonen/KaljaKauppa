package fi.tamk.beerbros.kaljakauppa.components.country;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

/**
 * Entity class for country entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Entity(name = "country")
public class Country implements Serializable {
    
    /**
     * Country name and id at the same time.
     */
    @Id
    @Column(name = "name", nullable = true, unique = true)
    private String name;
    
    /**
     * Country description text.
     */
    @Column(name = "description", nullable = true)
    private String description;
    
    /**
     * Beers under this country.
     */
    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private Collection<Beer> products;
    
    /**
     * Generates default Country object and is used by Sprint boot manager.
     */
    public Country() {}
    
    /**
     * Constructs country object with name and is used by fetching countries.
     * 
     * @param name Country name in String format.
     */
    public Country(String name) {this.name = name;}

    /**
     * Returns country's name.
     * 
     * @return Name of the country in String format.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name for the country entity.
     * 
     * @param name Name in String format.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns country's description text.
     * 
     * @return Description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description text.
     * 
     * @param description Description text.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a list of products.
     * 
     * @return List of products under this country.
     */
    public Collection<Beer> getProducts() {
        return products;
    }

    /**
     * Sets list of products under this country.
     * 
     * @param products List of product.
     */
    public void setProducts(Collection<Beer> products) {
        this.products = products;
    }
}
