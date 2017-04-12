package fi.tamk.beerbros.kaljakauppa.components;

import java.io.*;
import java.sql.Timestamp;
import org.springframework.boot.*;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import fi.tamk.beerbros.kaljakauppa.components.beer.*;
import fi.tamk.beerbros.kaljakauppa.components.country.*;
import fi.tamk.beerbros.kaljakauppa.components.beertype.*;
import fi.tamk.beerbros.kaljakauppa.components.manufacturer.*;
import org.springframework.beans.factory.annotation.Autowired;

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

                saveCountry(b);
                saveBeerType(b);
                saveManufacturer(b);

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
        int progression = (int) (((float) currentIndex / (float) maxIndex) * 100);

        System.out.printf("DATABASE INITIALIZING PROGRESS: %d/100", progression);
        System.out.println("");
    }
    
    private void saveCountry(Beer b) {
        final String countryDesc = String.format(
                        COUNTRY_DESCRIPTION,
                        b.getCountry().getName());
        Country c = b.getCountry();

        if(cr.findOne(c.getName()) == null) {
            c.setDescription(countryDesc);
            cr.save(c);
        }                  
    }
    
    private void saveManufacturer(Beer b) {
        final String manuDesc = String.format(
                        MANUFACTURER_DESCRIPTION,
                        b.getManufacturer().getName(),
                        b.getCountry().getName());
        
        Manufacturer m = b.getManufacturer();

        if(mr.findOne(m.getName()) == null) {
            m.setDescription(manuDesc);
            mr.save(m);
        } 
    }
    
    private void saveBeerType(Beer b) {
        final String typeDesc = String.format(
                        BEER_TYPE_DESCRIPTION,
                        b.getBeerType().getName(),
                        b.getBeerType().getName());
        BeerType bt = b.getBeerType();

        if(btr.findOne(bt.getName()) == null) {
            bt.setDescription(typeDesc);
            btr.save(bt);
        } 
    }
}
