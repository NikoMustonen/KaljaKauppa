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
import fi.tamk.beerbros.kaljakauppa.components.review.Review;
import fi.tamk.beerbros.kaljakauppa.components.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Class for loading dummy data to database.
 *
 * Runs before application starts.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Component
public class BeerDataLoader implements ApplicationRunner {

    /**
     * Review database table handler;
     */
    @Autowired
    ReviewRepository rr;

    /**
     * Currently used environment.
     */
    @Autowired
    Environment environment;

    /**
     * Manufacturer database table handler.
     */
    @Autowired
    ManufacturerRepository mr;

    /**
     * BeerType database table handler.
     */
    @Autowired
    BeerTypeRepository btr;

    /**
     * Country database table handler.
     */
    @Autowired
    CountryRepository cr;

    /**
     * Beer database table handler.
     */
    @Autowired
    BeerRepository br;

    /**
     * Dummy description for country entities.
     */
    private final String COUNTRY_DESCRIPTION
            = "Country called %s!";

    /**
     * Dummy description for beer type entities.
     */
    private final String BEER_TYPE_DESCRIPTION
            = "%s is a beer style which is kind of like %s.";

    /**
     * Dummy description for manufacturer entities.
     */
    private final String MANUFACTURER_DESCRIPTION
            = "%s is a beer brewing company from %s.";

    /**
     * Last progress bar value.
     */
    private int lastValue = 0;

    /**
     * Progress bar visual presentation.
     */
    private String progressBar = "";

    /**
     * Loading bars maximum length.
     */
    private final int LOADING_BAR_LENTGH = 30;

    /**
     * Starts database population.
     *
     * Populates database with some dummy data from JSON files.
     *
     * @param args Not used.
     */
    @Override
    public void run(ApplicationArguments args) {
        ObjectMapper mapper = new ObjectMapper();
        int currentIndex = 1;

        try {
            //Load JSON files
            InputStream isBeer
                    = new ClassPathResource("beers.json").getInputStream();
            InputStream isReviews
                    = new ClassPathResource("reviews.json").getInputStream();

            //Read and convert JSON files
            Beer[] beers = mapper.readValue(isBeer, Beer[].class);
            Review[] reviews = mapper.readValue(isReviews, Review[].class);
            System.out.println("\n\nPOPULATING DATABASE: ");

            /*Iterate through beer JSON file and add all the countries, 
            manufacturers, beertypes and beers.*/
            for (Beer b : beers) {

                saveCountry(b);
                saveBeerType(b);
                saveManufacturer(b);

                try {
                    b.setTimeAdded(new Timestamp(System.currentTimeMillis()));
                    br.save(b);
                } catch (Exception e) {
                    //This prevents failing when duplicate dummy data happens.
                }

                printLoadingProgress(currentIndex, beers.length);
                currentIndex++;
            }

            //Save dummy reviews for the first beer in the database.
            for (Review r : reviews) {
                rr.save(r);
            }

            //Get environment property for current environment
            String port = environment.getProperty("server.port");

            //Print instructions
            System.out.println(" --- DATABASE READY ---\n\n"
                    + "BACKEND APP RUNNING ON PORT: " + port + "\n");
            System.out.println("Kaljakauppa site: http://localhost:"
                    + port + "/index.html");
            System.out.println("Kaljakauppa api: http://localhost:"
                    + port + "/kaljakauppa\n\n");
        } catch (IOException e) {

            //Print error message if database population fails.
            System.out.println("Error while populating database:"
                    + e.getMessage());
        }
    }

    /**
     * Prints loading bar which indicates database loading progress.
     * 
     * @param currentIndex Current database loading progress.
     * @param maxIndex Maximum database loading progress.
     */
    private void printLoadingProgress(int currentIndex, int maxIndex) {
        int progression
                = (int) (((float) currentIndex / (float) maxIndex)
                * LOADING_BAR_LENTGH);

        if (progression > lastValue) {
            progressBar += "-";
            System.out.printf("|%-" + LOADING_BAR_LENTGH + "s|\r", progressBar);
            lastValue++;
        }
    }

    /**
     * Saves country entity to database.
     * 
     * @param b Country is fetched from this beer entity.
     */
    private void saveCountry(Beer b) {
        final String countryDesc = String.format(
                COUNTRY_DESCRIPTION,
                b.getCountry().getName());
        Country c = b.getCountry();

        if (cr.findOne(c.getName()) == null) {
            c.setDescription(countryDesc);
            cr.save(c);
        }
    }

    /**
     * Saves manufacturer entity to database.
     * 
     * @param b Manufacturer is fetched from this beer entity.
     */
    private void saveManufacturer(Beer b) {
        final String manuDesc = String.format(
                MANUFACTURER_DESCRIPTION,
                b.getManufacturer().getName(),
                b.getCountry().getName());

        Manufacturer m = b.getManufacturer();

        if (mr.findOne(m.getName()) == null) {
            m.setDescription(manuDesc);
            mr.save(m);
        }
    }

    /**
     * Saves Beer entity to database.
     * 
     * @param b Beer entity to be saved.
     */
    private void saveBeerType(Beer b) {
        final String typeDesc = String.format(
                BEER_TYPE_DESCRIPTION,
                b.getBeerType().getName(),
                b.getBeerType().getName());
        BeerType bt = b.getBeerType();

        if (btr.findOne(bt.getName()) == null) {
            bt.setDescription(typeDesc);
            btr.save(bt);
        }
    }
}
