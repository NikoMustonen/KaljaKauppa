package fi.tamk.beerbros.kaljakauppa.components;

import fi.tamk.beerbros.kaljakauppa.components.product.beer.BeerService;
import fi.tamk.beerbros.kaljakauppa.components.country.CountryService;
import fi.tamk.beerbros.kaljakauppa.components.manufacturer.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;

public class MainComponent {
    
    @Autowired
    protected BeerService beerService;
    
    @Autowired
    protected CountryService countryService;
    
    @Autowired
    protected ManufacturerService manufacturerService;
}
