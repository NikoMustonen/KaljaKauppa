package fi.tamk.beerbros.kaljakauppa.components.region;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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
}
