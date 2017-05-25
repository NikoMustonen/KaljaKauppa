package fi.tamk.beerbros.kaljakauppa.components.beertype;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

/**
 * Entity class for beer type entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Entity(name = "beer_type")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BeerType implements Serializable {

    /**
     * Beer type name which works as an id for the beer type.
     */
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Beer type description text.
     */
    @Column(name = "description")
    private String description;

    /**
     * List of beers by this beer type.
     */
    @OneToMany(mappedBy = "beerType")
    @JsonIgnore
    private Collection<Beer> products;

    /**
     * Default constructor which should be only used by Spring Boot entity
     * manager.
     */
    public BeerType() {
    }

    /**
     * Constructor which is used for fetching beer types from the database.
     *
     * @param name Beer type name.
     */
    public BeerType(String name) {
        this.name = name;
    }

    /**
     * Returns beer type name.
     *
     * @return Beer type name as a String format.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets beer type name text.
     *
     * @param name Name as a String format.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns beer type description.
     *
     * @return Beer type description as a String format.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets beer type description text.
     *
     * @param description Description text.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns all the products under this beer type.
     *
     * @return List of beers.
     */
    public Collection<Beer> getProducts() {
        return products;
    }

    /**
     * Sets list of beers under this beer type.
     *
     * @param products List of beers.
     */
    public void setProducts(Collection<Beer> products) {
        this.products = products;
    }
}
