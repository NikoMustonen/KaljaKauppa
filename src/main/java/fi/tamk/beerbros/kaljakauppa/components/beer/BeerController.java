package fi.tamk.beerbros.kaljakauppa.components.beer;

import com.google.common.collect.Lists;
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

@RestController
@RequestMapping("/kaljakauppa/beers")
public class BeerController {

    @Autowired
    private BeerRepository br;

    @Autowired
    BeerResourceAssembler resourceAssembler;

    @Autowired
    ReviewResourceAssembler reviewResourceAssembler;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<Beer>> getBeers(
            @RequestParam(value = "beertype", required = false) String beertype,
            @RequestParam(value = "manufacturer", required = false) String manufacturer,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "step", required = false) Integer step) {

        List<Beer> beers;
        Manufacturer m = (manufacturer == null) ? null : new Manufacturer(manufacturer);
        Country c = (country == null) ? null : new Country(country);
        BeerType bt = (beertype == null) ? null : new BeerType(beertype);
        
        if(m != null && c != null && bt != null) {
            beers = br.findByCountryAndManufacturerAndBeerType(c, m, bt);
        } else if(m == null && c != null && bt != null) {
            beers = br.findByBeerTypeAndCountry(bt, c);
        } else if(m == null && c == null && bt != null) {
            beers = br.findByBeerType(bt);
        } else if(m == null && c != null && bt != null) {
            beers = br.findByCountry(c);
        } else if(m != null && c == null && bt != null) {
            beers = br.findByBeerTypeAndManufacturer(bt, m);
        } else if(m != null && c == null && bt == null) {
            beers = br.findByManufacturer(m);
        } else if(m != null && c != null && bt == null) {
            beers = br.findByCountryAndManufacturer(c, m);
        } else {
            beers = Lists.newArrayList(br.findAll());
        }
            
        List<Resource<Beer>> beerResourceList = new ArrayList<>();

        if (start != null && start >= 0 && step != null && start + step < beers.size()) {
            for (int i = start - 1; i < start - 1 + step; i++) {
                beers.get(i).setReviews(null);
                beerResourceList.add(resourceAssembler.toResource(beers.get(i)));
            }
        } else {
            for (Beer b : beers) {
                b.setReviews(null);
                beerResourceList.add(resourceAssembler.toResource(b));
            }
        }
        Resources<Resource<Beer>> resources
                = new Resources<>(beerResourceList,
                        linkTo(BeerController.class).withSelfRel());
        if (start != null && start >= 1 && step != null && step > 0) {

            if (start > 1) {
                int first = (start - step > 1) ? start - step : 1;
                resources.add(linkTo(BeerController.class).slash("?start=" + first + "&step=" + step).withRel("previous"));
            }

            int nextFirst = start + step;
            int nextLast = start + step + step;
            if (nextLast < beers.size()) {
                resources.add(linkTo(BeerController.class).slash("?start=" + nextFirst + "&step=" + step).withRel("next"));
            }
        }

        return resources;
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Beer> getBeer(@PathVariable int id) {

        Beer b = br.findOne(id);

        b.setReviews(null);

        return this.resourceAssembler.toResource(b);
    }

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
                linkTo(BeerController.class).slash(id).slash("reviews").withSelfRel(),
                 linkTo(BeerController.class).slash(id).withRel("beer"));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addBeer(@RequestBody Beer beer) {
        beer.setTimeAdded(new Timestamp(System.currentTimeMillis()));
        br.save(beer);
    }

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
