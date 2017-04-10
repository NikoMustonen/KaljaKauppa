package fi.tamk.beerbros.kaljakauppa.components.country;

import fi.tamk.beerbros.kaljakauppa.components.beertype.BeerType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CountryRepository extends CrudRepository<Country, String>{
    
    @Query(value = "SELECT DISTINCT p FROM beer b LEFT JOIN b.beerType p WHERE b.country=:country")
    public Iterable<BeerType> findAllBeerTypesByCountryId(@Param("country") Country country);
    
    @Query(value = "SELECT DISTINCT p FROM beer b LEFT JOIN b.beerType p WHERE b.country=:country AND b.beerType=:beerType")
    public BeerType findBeerTypeByCountryAndType (
            @Param("country") Country country,
            @Param("beerType") BeerType beerType);
}
