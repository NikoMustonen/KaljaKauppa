package fi.tamk.beerbros.kaljakauppa.components.country;

import fi.tamk.beerbros.kaljakauppa.components.MainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {
    
    @Autowired
    CountryService cs;
    
    @RequestMapping(MainController.COUNTRIES)
    public Iterable<Country> addCountry() {
        return cs.getAllCountries();
    }
    
    @RequestMapping("/countries/{countryName}")
    public Country getCountryByName(@PathVariable("countryName") String name) {
        return cs.countryRepository.findOne(name);
    }
    
    @RequestMapping(value = "/countries", method = RequestMethod.POST)
    public void addCountry(@RequestBody Country country) {
        cs.addCountry(country);
    }
}
