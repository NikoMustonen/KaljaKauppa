package fi.tamk.beerbros.kaljakauppa.components.manufacturer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerService {
    
    @Autowired
    ManufacturerRepository manufacturerRepository;
    
    public Iterable<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }
    
    public void addManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }
}
