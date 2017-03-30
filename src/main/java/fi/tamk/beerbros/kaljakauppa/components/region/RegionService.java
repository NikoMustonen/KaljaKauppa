package fi.tamk.beerbros.kaljakauppa.components.region;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    @Autowired
    RegionRepository regionRepository;
    
    public Iterable<Region> findAll() {
        return regionRepository.findAll();
    }
    
    public Region findById(int id) {
        
        return regionRepository.findOne(id);
    }
    
    public void add(Region region) {
        regionRepository.save(region);
    }
    
    public void update(int id, Region region) {
        Region r = findById(id);
        r.setName(region.getName());
        regionRepository.save(r);
    }
    
    public void delete(int id) {
        regionRepository.delete(id);
    }
}
