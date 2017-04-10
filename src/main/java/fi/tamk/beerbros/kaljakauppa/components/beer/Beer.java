package fi.tamk.beerbros.kaljakauppa.components.beer;

import fi.tamk.beerbros.kaljakauppa.components.beertype.BeerType;
import fi.tamk.beerbros.kaljakauppa.components.manufacturer.Manufacturer;
import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "beer")
public class Beer implements Serializable {
    
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", length = 50, nullable = false, unique = true)
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
    
    @Column(name = "abv_percent")
    private Float abvPercent;
    
    @ManyToOne
    @JoinColumn(name = "beer_type", referencedColumnName = "name", nullable = true)
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
        this.country = new Country(country);
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
        
        if(price instanceof String) {
            String priceString = (String) price;
            priceString = priceString.replace(",", ".").trim();
            priceString = priceString.replaceAll("[^\\d.]", "");
            this.price = Float.parseFloat(priceString);
        } else if(price instanceof Float) {
            this.price = (Float)price;
        }
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
        if(abvPercent instanceof String) {
            String abv = (String) abvPercent;
            abv = abv.replace(",", ".").trim();
            abv = abv.replaceAll("[^\\d.]", "");
            this.abvPercent = Float.parseFloat(abv);
        } else if(price instanceof Float) {
            this.abvPercent = (Float)abvPercent;
        }
    }

    public BeerType getBeerType() {
        return beerType;
    }

    public void setBeerType(String beerType) {
        this.beerType = new BeerType(beerType);
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
        this.manufacturer = new Manufacturer(manufacturer);
    }

    public Float getIbuScale() {
        return ibuScale;
    }

    public void setIbuScale(Object ibuScale) {
        if(ibuScale instanceof String) {
            String ibu = (String) ibuScale;
            ibu = ibu.replaceFirst(",", ".");
            ibu = ibu.replaceAll("[^\\d.]", "");
            this.ibuScale = Float.parseFloat(ibu);
        } else if (ibuScale instanceof Float) {
            this.ibuScale = (Float)ibuScale; 
        }
    }

    public Float getPlatoScale() {
        return platoScale;
    }

    public void setPlatoScale(Object platoScale) {
        if(platoScale instanceof String) {
            String plato = (String) platoScale;
            plato = plato.replaceFirst(",", ".");
            plato = plato.replaceAll("[^\\d.]", "");
            this.platoScale = Float.parseFloat(plato);
        } else if (ibuScale instanceof Float) {
            this.platoScale = (Float)platoScale; 
        }
    }

    public Float getEbc() {
        return ebc;
    }

    public void setEbc(Object ebcScale) {
        if(ebcScale instanceof String) {
            String ebcS = (String) ebcScale;
            ebcS = ebcS.replaceFirst(",", ".");
            ebcS = ebcS.replaceAll("[^\\d.]", "");
            this.ebc = Float.parseFloat(ebcS);
        } else if (ibuScale instanceof Float) {
            this.ebc = (Float)ebcScale; 
        }
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }
}
