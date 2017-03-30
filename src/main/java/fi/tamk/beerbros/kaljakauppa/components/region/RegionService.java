package fi.tamk.beerbros.kaljakauppa.components.region;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RegionService {
    
    private final List<Region> regions = Arrays.asList(
            new Region(1, "Sweden"),
            new Region(2, "Finland"),
            new Region(3, "Norway"),
            new Region(4, "Denmark"),
            new Region(5, "England"),
            new Region(6, "Spain"),
            new Region(7, "New Zeland")
    );
    
    public Iterable<Region> findAll() {
        return regions;
    }
}
