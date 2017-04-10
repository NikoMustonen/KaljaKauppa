package fi.tamk.beerbros.kaljakaupparedo.components.beertype;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fi.tamk.beerbros.kaljakaupparedo.components.beer.Beer;
import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity(name = "beer_type")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BeerType implements Serializable {

    @Id
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "beerType")
    @JsonIgnore
    private Collection<Beer> products;
    
    public BeerType(){}
    
    public BeerType(String name){
        this.name = name;
    }

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
