package fi.tamk.beerbros.kaljakauppa.components.beer;

import com.google.common.collect.Lists;
import fi.tamk.beerbros.kaljakauppa.components.MainResourceAssembler;
import fi.tamk.beerbros.kaljakauppa.components.beertype.BeerType;
import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import fi.tamk.beerbros.kaljakauppa.components.manufacturer.Manufacturer;
import fi.tamk.beerbros.kaljakauppa.components.review.Review;
import fi.tamk.beerbros.kaljakauppa.components.review.ReviewResourceAssembler;
import java.sql.Timestamp;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Rest Controller class for beer data.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@RestController
@RequestMapping("/kaljakauppa/beers")
public class BeerController {

    /**
     * Beer data handler.
     */
    @Autowired
    private BeerRepository br;

    /**
     * Beer HATEOAS resource generator.
     */
    @Autowired
    BeerResourceAssembler resourceAssembler;

    /**
     * Review data handler.
     */
    @Autowired
    ReviewResourceAssembler reviewResourceAssembler;

    /**
     * Get all beers.
     *
     * Fetched beer data can be filtered with filtering key words by the for of
     * request parameters. ?start=1&step=10 will return 10 first beers.
     * ?country=viro&beertype=erikoisuus will return all the special beers from
     * Estonia for example. Parameters can be combined with each other but there
     * is one constraint. "start" and "step" parameters must be used together.
     *
     * @param beertype Beer type name as a String format.
     * @param manufacturer Manufacturer name as a String format.
     * @param country Country name as a String format.
     * @param start Starting position.
     * @param step Defines the amount of beers to be fetched.
     * @return
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<Beer>> getBeers(
            @RequestParam(value = "beertype", required = false) String beertype,
            @RequestParam(value
                    = "manufacturer", required = false) String manufacturer,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "step", required = false) Integer step) {

        //List of beers which will be populated with beers from the database.
        List<Beer> beers;

        /*
        Create manufacturer, country and beertype elements if they exists
        in the request parameters list.
         */
        Manufacturer m = (manufacturer == null)
                ? null : new Manufacturer(manufacturer);
        Country c = (country == null) ? null : new Country(country);
        BeerType bt = (beertype == null) ? null : new BeerType(beertype);

        //Check filtering level
        if (m != null && c != null && bt != null) {
            System.out.println("Debug: 1");
            beers = br.findByCountryAndManufacturerAndBeerType(c, m, bt);
        } else if (m == null && c != null && bt != null) {
            System.out.println("Debug: 2");
            beers = br.findByBeerTypeAndCountry(bt, c);
        } else if (m == null && c == null && bt != null) {
            System.out.println("Debug: 3");
            beers = br.findByBeerType(bt);
        } else if (m == null && c != null && bt == null) {
            System.out.println("Debug: 4");
            beers = br.findByCountry(c);
        } else if (m != null && c == null && bt != null) {
            System.out.println("Debug: 5");
            beers = br.findByBeerTypeAndManufacturer(bt, m);
        } else if (m != null && c == null && bt == null) {
            System.out.println("Debug: 6");
            beers = br.findByManufacturer(m);
        } else if (m != null && c != null && bt == null) {
            System.out.println("Debug: 7");
            beers = br.findByCountryAndManufacturer(c, m);
        } else {
            System.out.println("Debug: 8");
            beers = Lists.newArrayList(br.findAll());
        }

        //Resource list for fetched beer for HATEOAS linking.
        List<Resource<Beer>> beerResourceList = new ArrayList<>();

        //Check amount and position of beers to be fetched
        if (start != null //Start position not null
                && start >= 0 //Start position not zero
                && step != null //Step not null
                && start + step < beers.size()) {   //Next step array overlaps
            for (int i = start - 1; i < start - 1 + step; i++) {
                beers.get(i).setReviews(null);
                beerResourceList.add(// Add generated resource to list.
                        resourceAssembler.toResource(beers.get(i)));
            }
        } else {
            //If no filtering is needed this happens.
            for (Beer b : beers) {
                b.setReviews(null);
                beerResourceList.add(resourceAssembler.toResource(b));
            }
        }

        //Generate new Resources object with Resource array
        //Add HATEOAS link
        Resources<Resource<Beer>> resources
                = new Resources<>(beerResourceList,
                        linkTo(BeerController.class).withSelfRel());

        //Generates previous and next beer step HATEOAS links if possible
        if (start != null && start >= 1 && step != null && step > 0) {

            //Generate previous page link
            if (start > 1) {
                int first = (start - step > 1) ? start - step : 1;
                resources.add(linkTo(BeerController.class).
                        slash("?start=" + first + "&step=" + step).
                        withRel("previous"));
            }

            //Generate next page link
            int nextFirst = start + step;
            int nextLast = start + step + step;
            if (nextLast < beers.size()) {
                resources.add(linkTo(BeerController.class).
                        slash("?start=" + nextFirst + "&step=" + step).
                        withRel("next"));
            }
        }

        //Add main resource HATEOAS links
        MainResourceAssembler.addLinksToResources(resources);
        return resources;
    }

    /**
     * Fetches the corresponding beer defined by the id number.
     *
     * @param id Beer identification number.
     * @return Beer corresponding to id number.
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Beer> getBeer(@PathVariable int id) {

        Beer b = br.findOne(id);

        b.setReviews(null);
        Resource r = this.resourceAssembler.toResource(b);
        return r;
    }

    /**
     * Returns review entity by beer id and review id.
     *
     * @param id Beer id.
     * @param reviewId Review id.
     * @return Review object.
     */
    @RequestMapping(
            value = "/{id}/reviews/{reviewId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Review> getReviewByBeerAndId(
            @PathVariable int id, @PathVariable long reviewId) {

        Beer b = br.findOne(id);

        for (Review r : b.getReviews()) {
            if (r.getId() == reviewId) {
                r.getBeer().setReviews(null);
                return reviewResourceAssembler.toResource(r);
            }
        }

        return null;
    }

    /**
     * Returns all reviews by chosen beer.
     *
     * @param id Beer id.
     * @return All the reviews for this beer.
     */
    @RequestMapping(
            value = "/{id}/reviews",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<Review>> getReviewsByBeer(@PathVariable int id) {

        Beer b = br.findOne(id);
        List<Resource<Review>> reviewsByBeer = new ArrayList<>();

        for (Review r : b.getReviews()) {
            reviewsByBeer.add(reviewResourceAssembler.toResource(r));
            r.setBeerNull();
        }

        return new Resources<>(reviewsByBeer,
                linkTo(BeerController.class).slash(id). //Link to reviews.
                        slash("reviews").
                        withSelfRel(),
                linkTo(BeerController.class). //Link to reviewed beer.
                        slash(id).
                        withRel("beer"));
    }

    /**
     * Adds new beer to database.
     *
     * Beer must be sent in JSON format.
     *
     * @param beer Beer entity.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addBeer(@RequestBody Beer beer) {
        beer.setTimeAdded(new Timestamp(System.currentTimeMillis()));
        br.save(beer);
    }

    /**
     * Modifies chosen beer.
     *
     * @param id Beer id of beer to be modified.
     * @param beer New values to be set to modified beer.
     * @return Beer data in JSON format.
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Beer> modifyBeer(
            @PathVariable int id,
            @RequestBody Beer beer) {
        Beer b = br.findOne(id);
        if (b != null) {
            b = beer;
            b.setId(id);
        }
        return resourceAssembler.toResource(br.save(b));
    }

    /**
     * Delete chosen beer from the database.
     *
     * @param id Beer id of beer to be deleted.
     * @return Resource with HATEOAS links and data of deleted beer.
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Beer> deleteBeer(
            @PathVariable int id) {
        Beer b = br.findOne(id);
        br.delete(b);
        return new Resource<>(b, linkTo(BeerController.class).withRel("beers"));
    }
}
