package fi.tamk.beerbros.kaljakauppa.components.region;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class RegionController {
    
    @Autowired
    private RegionService regionService;
    
    @RequestMapping(
        value = "/regions",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Region> getAllRegions() {
        
        return regionService.findAll();
    }
    
    @RequestMapping(
        value = "/regions/{id}")
    public Region getRegionById(@PathVariable(name = "id") int id) {
        return regionService.findById(id);
    }
    
    @RequestMapping(
        value = "/regions",
        method = RequestMethod.POST)
    public Region addRegion(@RequestBody Region region) {
        
        regionService.add(region);
        return region;
    }
    
    @RequestMapping(
        value = "/regions/{id}",
        method = RequestMethod.PUT)
    public void updateRegion(@RequestBody Region region, @PathVariable int id) {
        regionService.update(id, region);
    }
    
    @RequestMapping(
        value = "/regions/{id}",
        method = RequestMethod.DELETE)
    public void deleteRegion(@PathVariable int id) {
        regionService.delete(id);
    }
}
