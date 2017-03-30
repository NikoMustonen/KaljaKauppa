package fi.tamk.beerbros.kaljakauppa.rest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Regions {
    
    @RequestMapping("/regions")
    public String getAllRegions() {
        return "all regions";
    }
}
