package fi.tamk.beerbros.kaljakauppa.components.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    
    @Autowired
    CountryRepository countryRepository;
    
    public Iterable<Country> getAllCountries() {
        return countryRepository.findAll();
    }
    
    public void addCountry(Country country) {
        countryRepository.save(country);
    }
}
