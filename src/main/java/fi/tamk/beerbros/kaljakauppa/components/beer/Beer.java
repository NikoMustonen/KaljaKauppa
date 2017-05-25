package fi.tamk.beerbros.kaljakauppa.components.beer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tamk.beerbros.kaljakauppa.components.beertype.BeerType;
import fi.tamk.beerbros.kaljakauppa.components.manufacturer.Manufacturer;
import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import fi.tamk.beerbros.kaljakauppa.components.review.Review;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
@Entity(name = "beer")
public class Beer implements Serializable {
    
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", length = 255, nullable = false, unique = false)
    protected String name;

    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "name", nullable = false)
    protected Country country;
    
    @Column(name = "img_url")
    protected String imgUrl;
    
    @Column(name = "price")
    protected Float price;
    
    @Column(name = "volume")
    protected String volume;
    
    @Column(name = "description")
    protected String description;
    
    @Column(name = "abv_percent", nullable = false)
    private Float abvPercent;
    
    @ManyToOne
    @JoinColumn(name = "beer_type", referencedColumnName = "name", nullable = false)
    protected BeerType beerType;
    
    @Column(name = "price_per_liter")
    protected String pricePerLitre;
    
    @Column(name = "package_type")
    protected String packageType;
    
    @ManyToOne
    @JoinColumn(name = "manufacturer", referencedColumnName = "name", nullable = false)
    protected Manufacturer manufacturer;
    
    @Column(name = "ibu_scale")
    protected Float ibuScale;
    
    @Column(name = "plato_scale")
    protected Float platoScale;
    
    @Column(name = "ebc")
    protected Float ebc;
    
    @Column(name = "time_added")
    private Timestamp timeAdded;
    
    @OneToMany(mappedBy = "beer")
    private Collection<Review> reviews;
    
    public Beer() {}
    
    public Beer(Integer id) {this.id = id;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = new Country(country.toLowerCase());
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = getCleanedFloatValue(price);
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAbvPercent() {
        return abvPercent;
    }

    public void setAbvPercent(Object abvPercent) {
        this.abvPercent = getCleanedFloatValue(abvPercent);
    }

    public BeerType getBeerType() {
        return beerType;
    }

    public void setBeerType(String beerType) {
        this.beerType = new BeerType(beerType.toLowerCase());
    }

    public String getPricePerLitre() {
        return pricePerLitre;
    }

    public void setPricePerLitre(String pricePerLitre) {
        this.pricePerLitre = pricePerLitre;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = new Manufacturer(manufacturer.toLowerCase());
    }

    public Float getIbuScale() {
        return ibuScale;
    }

    public void setIbuScale(Object ibuScale) {
        this.ibuScale = getCleanedFloatValue(ibuScale);
    }

    public Float getPlatoScale() {
        return platoScale;
    }

    public void setPlatoScale(Object platoScale) {
        this.platoScale = getCleanedFloatValue(platoScale);
    }

    public Float getEbc() {
        return ebc;
    }

    public void setEbc(Object ebcScale) {
        this.ebc = getCleanedFloatValue(ebcScale);
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }
    
    @JsonIgnore
    private Float getCleanedFloatValue(Object o) {
        if(o instanceof String) {
            String strVal = (String) o;
            strVal = strVal.replaceFirst(",", ".");
            strVal = strVal.replaceAll("[^\\d.]", "");
            return Float.parseFloat(strVal);
        } else if (ibuScale instanceof Float) {
            return (Float)o; 
        }
        return null;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }
}
