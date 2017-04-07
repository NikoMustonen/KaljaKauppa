package fi.tamk.beerbros.kaljakauppa.components.product.beer;

import fi.tamk.beerbros.kaljakauppa.components.product.AlcoholicBeverage;
import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "BEER")
public class Beer extends AlcoholicBeverage {
    
    @Column(name = "plato_scale")
    protected Float platoScale;
    
    @Column(name = "ibu_scale")
    protected Float ibuScale;
    
    @Column(name = "ebc")
    protected Float ebc;
    
    public Beer() {}

    public Float getPlatoScale() {
        return platoScale;
    }

    public void setPlatoScale(Float platoScale) {
        this.platoScale = platoScale;
    }

    public Float getIbuScale() {
        return ibuScale;
    }

    public void setIbuScale(Float ibuScale) {
        this.ibuScale = ibuScale;
    }

    public Float getEbc() {
        return ebc;
    }

    public void setEbc(Float ebc) {
        this.ebc = ebc;
    }
}
