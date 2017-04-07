package fi.tamk.beerbros.kaljakauppa.components.manufacturer;

import fi.tamk.beerbros.kaljakauppa.components.MainComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManufacturerController extends MainComponent{
    
    @RequestMapping("/manufacturers")
    public Iterable<Manufacturer> addCountry() {
        return manufacturerService.getAllManufacturers();
    }
    
    @RequestMapping(value = "/manufacturers", method = RequestMethod.POST)
    public void addCountry(@RequestBody Manufacturer manufacturer) {
        manufacturerService.addManufacturer(manufacturer);
    }
}
