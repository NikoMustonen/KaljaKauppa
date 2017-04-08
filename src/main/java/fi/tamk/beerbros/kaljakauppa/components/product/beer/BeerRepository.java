package fi.tamk.beerbros.kaljakauppa.components.product.beer;

import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import fi.tamk.beerbros.kaljakauppa.components.type.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BeerRepository extends CrudRepository<Beer, Integer> {
    
    @Query(value = "SELECT b FROM beer b WHERE b.country=:country")
    public Iterable<Beer> findBeerByCountry(@Param("country") Country country);
    
    @Query(value = "SELECT b FROM beer b WHERE b.country=:country AND b.id=:id")
    public Beer findBeerByCountryAndId(@Param("country") Country country, @Param("id") Integer id);
    
    @Query(value = "SELECT b FROM beer b WHERE b.beerType=:type")
    public Iterable<Beer> findBeersByType(@Param("type") Type type);
    
    @Query(value = "SELECT b FROM beer b WHERE b.beerType=:type AND b.id=:id")
    public Beer findBeerByTypeAndId(@Param("type") Type type, @Param("id") Integer id);
    
    @Query(value = "SELECT b FROM beer b WHERE b.beerType=:type AND b.country=:country")
    public Iterable<Beer> findBeersByTypeAndCountry(@Param("type") Type type, @Param("country") Country country);
    
    @Query(value = "SELECT b FROM beer b WHERE b.beerType=:type AND b.country=:country AND b.id=:id")
    public Beer findBeerByTypeAndCountryAndId(@Param("type") Type type, @Param("country") Country country, @Param("id") Integer id);
}
