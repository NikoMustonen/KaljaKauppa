package fi.tamk.beerbros.kaljakauppa.util;

import java.io.File;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;

public class BeerJsonDataHandler {

    public static void addBeerDataToDatabase() {
        try {
            File file = new ClassPathResource("beers.json").getFile();
            
            for(int i = 0; i < 100; i++) {
                System.out.println(file.isFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
