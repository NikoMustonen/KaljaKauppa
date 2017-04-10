package fi.tamk.beerbros.kaljakauppa.components.country;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
//Test comment
@Entity(name = "country")
public class Country implements Serializable {
    
    @Id
    @Column(name = "name", nullable = true, unique = true)
    private String name;
    
    @Column(name = "description", nullable = true)
    private String description;
    
    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private Collection<Beer> products;
    
    public Country() {}
    
    public Country(String name) {this.name = name;}

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
