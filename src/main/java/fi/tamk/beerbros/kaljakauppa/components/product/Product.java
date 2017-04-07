package fi.tamk.beerbros.kaljakauppa.components.product;

import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import fi.tamk.beerbros.kaljakauppa.components.manufacturer.Manufacturer;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

@Entity(name = "product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Product implements Serializable{
    
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", length = 50, nullable = false)
    protected String name;
    
    @ManyToOne
    @JoinColumn(name = "manufacturer", referencedColumnName = "name", nullable = false)
    protected Manufacturer manufacturer;
    
    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "name", nullable = false)
    protected Country country;
    
    @Column(name = "price_euros", nullable = false)
    protected Float priceEuros;
    
    @Column(name = "img_url")
    protected String imgUrl;
    
    @Column(name = "description")
    protected String description;
    
    @Column(name = "time_added")
    private Timestamp timeAdded;
    
    public Product() {}
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer.getName();
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = new Manufacturer(manufacturer, "");
    }

    public String getCountry() {
        return country.getName();
    }

    public void setCountry(String country) {
        this.country = new Country(country, "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPriceEuros() {
        return priceEuros;
    }

    public void setPriceEuros(Float priceEuros) {
        this.priceEuros = priceEuros;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp lastTouched) {
        this.timeAdded = lastTouched;
    }
}
