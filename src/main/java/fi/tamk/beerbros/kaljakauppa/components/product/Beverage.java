package fi.tamk.beerbros.kaljakauppa.components.product;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "beverage")
@DiscriminatorValue(value = "BEVERAGE")
public class Beverage extends Product{
    
    @Column(name = "serving_size_liters")
    protected Float servingSizeLiters;
    
    @Column(name = "pack_type")
    protected String packType;
    
    public Beverage() {}

    public Float getServingSizeLiters() {
        return servingSizeLiters;
    }

    public void setServingSizeLiters(Float servingSizeLiters) {
        this.servingSizeLiters = servingSizeLiters;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }
}
