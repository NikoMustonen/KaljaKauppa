package fi.tamk.beerbros.kaljakauppa.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import fi.tamk.beerbros.kaljakauppa.components.beer.BeerRepository;
import fi.tamk.beerbros.kaljakauppa.components.beertype.BeerTypeRepository;
import fi.tamk.beerbros.kaljakauppa.components.country.CountryRepository;
import fi.tamk.beerbros.kaljakauppa.components.manufacturer.ManufacturerRepository;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class BeerDataLoader implements ApplicationRunner {

    @Autowired
    ManufacturerRepository mr;

    @Autowired
    BeerTypeRepository btr;

    @Autowired
    CountryRepository cr;

    @Autowired
    BeerRepository br;

    private final String COUNTRY_DESCRIPTION
            = "Country called %s!";
    
    private final String BEER_TYPE_DESCRIPTION
            = "%s is a beer style which is kind of like %s.";
    
    private final String MANUFACTURER_DESCRIPTION
            = "%s is a beer brewing company from %s.";

    @Override
    public void run(ApplicationArguments args) {
        ObjectMapper mapper = new ObjectMapper();
        int currentIndex = 1;
        
        try {
            //File file = new ClassPathResource("beers.json").getFile();
            InputStream is = new ClassPathResource("beers.json").getInputStream();
            Beer[] beers = mapper.readValue(is, Beer[].class);
            for (Beer b : beers) {

                String manuDesc = String.format(
                        MANUFACTURER_DESCRIPTION,
                        b.getManufacturer().getName(),
                        b.getCountry().getName());

                String typeDesc = String.format(
                        BEER_TYPE_DESCRIPTION,
                        b.getBeerType().getName(),
                        b.getBeerType().getName());

                String countryDesc = String.format(
                        COUNTRY_DESCRIPTION,
                        b.getCountry().getName());

                b.getManufacturer().setDescription(manuDesc);
                b.getBeerType().setDescription(typeDesc);
                b.getCountry().setDescription(countryDesc);

                mr.save(b.getManufacturer());
                cr.save(b.getCountry());
                btr.save(b.getBeerType());

                try {
                    b.setTimeAdded(new Timestamp(System.currentTimeMillis()));
                    br.save(b);
                } catch (Exception e) {
                    //System.out.println("DUBLICATE VALUE!!!");
                }
                
                printLoadingProgress(currentIndex, beers.length);
                currentIndex++;
            }
            
            System.out.println("\n\nDATABASE READY\nAPP RUNNING...\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printLoadingProgress(int currentIndex, int maxIndex) {
        int progression = (int)(((float)currentIndex / (float) maxIndex) * 100);
        
        System.out.printf("DATABASE INITIALIZING PROGRESS: %d/100", progression);
        System.out.println("");
    }
}