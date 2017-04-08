package fi.tamk.beerbros.kaljakauppa.components.country;

import fi.tamk.beerbros.kaljakauppa.components.MainComponent;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController extends MainComponent {
    
    @RequestMapping("/countries")
    public Iterable<Country> addCountry() {
        return countryService.getAllCountries();
    }
    
    @RequestMapping("/countries/{countryName}")
    public Country getCountryByName(@PathVariable("countryName") String name) {
        return countryService.countryRepository.findOne(name);
    }
    
    @RequestMapping(value = "/countries", method = RequestMethod.POST)
    public void addCountry(@RequestBody Country country) {
        countryService.addCountry(country);
    }
}
