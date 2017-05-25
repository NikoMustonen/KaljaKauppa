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
 * Entity class for beer entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Entity(name = "beer")
public class Beer implements Serializable {

    /**
     * Beer identification number.
     */
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Beer name.
     */
    @Column(name = "name", length = 255, nullable = false, unique = false)
    protected String name;

    /**
     * Manufacturing country.
     */
    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "name", nullable = false)
    protected Country country;

    /**
     * Image identification number.
     */
    @Column(name = "img_url")
    protected String imgUrl;

    /**
     * Beer price.
     */
    @Column(name = "price")
    protected Float price;

    /**
     * Alcohol volume.
     */
    @Column(name = "volume")
    protected String volume;

    /**
     * Beer description.
     */
    @Column(name = "description")
    protected String description;

    /**
     * ABV percent.
     */
    @Column(name = "abv_percent", nullable = false)
    private Float abvPercent;

    /**
     * Type of beer.
     */
    @ManyToOne
    @JoinColumn(name = "beer_type", referencedColumnName = "name", nullable = false)
    protected BeerType beerType;

    /**
     * Beers price per litre.
     */
    @Column(name = "price_per_liter")
    protected String pricePerLitre;

    /**
     * Packacking type.
     */
    @Column(name = "package_type")
    protected String packageType;

    /**
     * Manufacturer entity for the beer.
     */
    @ManyToOne
    @JoinColumn(name = "manufacturer", referencedColumnName = "name", nullable = false)
    protected Manufacturer manufacturer;

    /**
     * Beers IBU scale.
     */
    @Column(name = "ibu_scale")
    protected Float ibuScale;

    /**
     * Beers Plato scale.
     */
    @Column(name = "plato_scale")
    protected Float platoScale;

    /**
     * Beers EBC value.
     */
    @Column(name = "ebc")
    protected Float ebc;

    /**
     * Timestamp of adding the beer to the system.
     */
    @Column(name = "time_added")
    private Timestamp timeAdded;

    /**
     * List of beers reviews.
     */
    @OneToMany(mappedBy = "beer")
    private Collection<Review> reviews;

    /**
     * Creates default Beer object and is used by Spring boot entity manager.
     */
    public Beer() {
    }

    /**
     * Creates beer with given id.
     *
     * Is used to fetch beer data from database.
     *
     * @param id Beer identification number.
     */
    public Beer(Integer id) {
        this.id = id;
    }

    /**
     * Returns beers identification number.
     *
     * @return Beers id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets beers identification number.
     *
     * Only used by Spring Boot framework.
     *
     * @param id Beer id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns beers name.
     *
     * @return Beers name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets beers name.
     *
     * @param name Beers new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns beers manufacturing country.
     *
     * @return Country which manufactures this beer.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets country for current beer.
     *
     * @param country New country.
     */
    public void setCountry(String country) {
        this.country = new Country(country.toLowerCase());
    }

    /**
     * Returns images identification number.
     *
     * @return Image id.
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * Sets images identification number.
     *
     * @param imgUrl images new id.
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * Returns beers price.
     *
     * @return Beers price.
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Sets beers price.
     *
     * @param price Beers new price.
     */
    public void setPrice(Object price) {
        this.price = getCleanedFloatValue(price);
    }

    /**
     * Returns beers volume.
     *
     * @return Beer volume.
     */
    public String getVolume() {
        return volume;
    }

    /**
     * Sets beers volume.
     *
     * @param volume Beers new volume.
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * Returns beers description.
     *
     * @return Beers description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets beers description.
     *
     * @param description New description for the beer.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns beers ABV percent.
     *
     * @return Beers ABV percent.
     */
    public Float getAbvPercent() {
        return abvPercent;
    }

    /**
     * Sets ABV percent for beer.
     *
     * @param abvPercent New ABV percent.
     */
    public void setAbvPercent(Object abvPercent) {
        this.abvPercent = getCleanedFloatValue(abvPercent);
    }

    /**
     * Returns type of the beer.
     *
     * @return BeerType object.
     */
    public BeerType getBeerType() {
        return beerType;
    }

    /**
     * Sets beer type for the beer.
     *
     * BeerType is made from string to make it easier to generate beer entities
     * from the frontend.
     *
     * @param beerType BeerType identifier string.
     */
    public void setBeerType(String beerType) {
        this.beerType = new BeerType(beerType.toLowerCase());
    }

    /**
     * Returns beer price per litre.
     *
     * @return Beers price per litre.
     */
    public String getPricePerLitre() {
        return pricePerLitre;
    }

    /**
     * Sets beers price per litre.
     *
     * @param pricePerLitre New price per litre.
     */
    public void setPricePerLitre(String pricePerLitre) {
        this.pricePerLitre = pricePerLitre;
    }

    /**
     * Returns package type.
     *
     * @return Beers package type.
     */
    public String getPackageType() {
        return packageType;
    }

    /**
     * Sets beers package type.
     *
     * @param packageType Package type in string format.
     */
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    /**
     * Returns beer manufacturer.
     * 
     * @return Manufacturer object.
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets beers manufacturer.
     * 
     * @param manufacturer New manufacturer object for beer.
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = new Manufacturer(manufacturer.toLowerCase());
    }

    /**
     * Returns beers IBU scale.
     * 
     * @return IBU scale.
     */
    public Float getIbuScale() {
        return ibuScale;
    }

    /**
     * Sets beers IBU scale.
     * 
     * @param ibuScale New IBU scale for the beer.
     */
    public void setIbuScale(Object ibuScale) {
        this.ibuScale = getCleanedFloatValue(ibuScale);
    }

    /**
     * Returns beers platoscale.
     * 
     * @return Beers Plato scale.
     */
    public Float getPlatoScale() {
        return platoScale;
    }

    /**
     * Sets beers Plato scale.
     * 
     * @param platoScale Plato scale.
     */
    public void setPlatoScale(Object platoScale) {
        this.platoScale = getCleanedFloatValue(platoScale);
    }

    /**
     * Returns beers EBC scale.
     * 
     * @return Beers EBC scale.
     */
    public Float getEbc() {
        return ebc;
    }

    /**
     * Sets beers EBC scale.
     * 
     * @param ebcScale EBC scale.
     */
    public void setEbc(Object ebcScale) {
        this.ebc = getCleanedFloatValue(ebcScale);
    }

    /**
     * Returns beer adding time.
     * 
     * @return Timestamp of beer adding.
     */
    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    /**
     * Sets beer adding time.
     * 
     * @param timeAdded Adding time. 
     */
    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    /**
     * Converts values to cleaner format for JSON.
     * 
     * @param o Values to be converted.
     * @return Converted value.
     */
    @JsonIgnore
    private Float getCleanedFloatValue(Object o) {
        if (o instanceof String) {
            String strVal = (String) o;
            strVal = strVal.replaceFirst(",", ".");
            strVal = strVal.replaceAll("[^\\d.]", "");
            return Float.parseFloat(strVal);
        } else if (ibuScale instanceof Float) {
            return (Float) o;
        }
        return null;
    }

    /**
     * Returns reviews of this beer.
     * 
     * @return Reviews as a Collection object.
     */
    public Collection<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets review Collection object for this beer.
     * 
     * @param reviews Review Collection object.
     */
    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }
}
