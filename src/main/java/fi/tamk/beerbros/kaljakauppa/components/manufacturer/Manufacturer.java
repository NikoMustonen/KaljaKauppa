package fi.tamk.beerbros.kaljakauppa.components.manufacturer;
    
import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tamk.beerbros.kaljakauppa.components.product.*;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity(name = "manufacturer")
public class Manufacturer implements Serializable {
    
    @Id
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "manufacturer")
    @JsonIgnore
    private List<Product> products;
    
    public Manufacturer(){}
    
    public Manufacturer(String name, String description) {
        this.name = name;
        this.description = description;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
