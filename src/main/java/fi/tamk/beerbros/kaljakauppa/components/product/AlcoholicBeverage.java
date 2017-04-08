package fi.tamk.beerbros.kaljakauppa.components.product;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "alcoholic_beverage")
@DiscriminatorValue(value = "ALCOHOLIC")
public class AlcoholicBeverage extends Beverage {
    
    private Float abv_percent;
    
    public AlcoholicBeverage() {}

    public Float getAbv_percent() {
        return abv_percent;
    }

    public void setAbv_percent(Float abv_percent) {
        this.abv_percent = abv_percent;
    }
}
