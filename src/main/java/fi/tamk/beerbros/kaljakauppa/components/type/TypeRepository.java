package fi.tamk.beerbros.kaljakauppa.components.type;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TypeRepository extends CrudRepository<Type, String> {
    
    @Query("SELECT t FROM type t WHERE t.mainType IS NULL")
    public Iterable<Type> findMainTypes();
    
    @Query("SELECT t FROM type t WHERE t.mainType = :mainType")
    public Iterable<Type> findSubTypesByMainType(@Param("mainType") Type mainType);
}
