package fi.tamk.beerbros.kaljakauppa.components.beer;

import fi.tamk.beerbros.kaljakauppa.components.beertype.BeerType;
import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import fi.tamk.beerbros.kaljakauppa.components.manufacturer.Manufacturer;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface BeerRepository extends CrudRepository<Beer, Integer>{
    
    public List<Beer> findByCountry(Country country);
    
    public List<Beer> findByBeerType(BeerType beerType);
    
    public List<Beer> findByManufacturer(Manufacturer manufacturer);
    
    public List<Beer> findByBeerTypeAndCountry(BeerType beerType, Country country);
    
    public List<Beer> findByBeerTypeAndManufacturer(BeerType beerType, Manufacturer manufacturer);
    
    public List<Beer> findByCountryAndManufacturer(Country country, Manufacturer manufacturer);
    
    public List<Beer> findByCountryAndManufacturerAndBeerType(Country country, Manufacturer manufacturer, BeerType beerType);
}
