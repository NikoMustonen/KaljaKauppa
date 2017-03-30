package fi.tamk.beerbros.kaljakauppa.components.region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RegionService {
    
    private List<Region> regions = new ArrayList<>();
    
    public RegionService() {
        regions.add(new Region(1, "Sweden"));
    }
    
    public Iterable<Region> findAll() {
        return regions;
    }
    
    public Region findById(int id) {
        
        return regions.stream().filter(r -> r.getId() == id).findFirst().get();
    }
    
    public void add(Region region) {
        regions.add(region);
    }
    
    public void update(int id, Region region) {
        Region r = findById(id);
        r.setName(region.getName());
    }
    
    public void delete(int id) {
        regions.remove(findById(id));
    }
}
