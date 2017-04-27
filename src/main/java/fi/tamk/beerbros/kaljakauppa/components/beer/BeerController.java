package fi.tamk.beerbros.kaljakauppa.components.beer;

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
    public Resources<Resource<Beer>> getBeers() {
        
        Iterable<Beer> beers = br.findAll();
        List<Resource<Beer>> beerResourceList = new ArrayList<>();
        
        for(Beer b : beers) {
            b.setReviews(null);
            beerResourceList.add(resourceAssembler.toResource(b));
        }
        
        return new Resources<>(beerResourceList, linkTo(BeerController.class).withSelfRel());
    }
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Beer> getBeer(@PathVariable int id) {
        
        Beer b = br.findOne(id);
        
        for(Review r : b.getReviews()) {
            r.setBeer(null);
        }
        
        return this.resourceAssembler.toResource(b);
    }
    
    @RequestMapping(
            value = "/{id}/reviews/{reviewId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Review> getReviewByBeerAndId(
            @PathVariable int id, @PathVariable long reviewId) {
        
        Beer b = br.findOne(id);
        
        for(Review r : b.getReviews()) {
            if(r.getId() == reviewId) {
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
        
        for(Review r : b.getReviews()) {
            reviewsByBeer.add(reviewResourceAssembler.toResource(r));
            r.setBeerNull();
        }
        
        return new Resources<>(reviewsByBeer, 
                linkTo(BeerController.class).slash(id).slash("reviews").withSelfRel()
                ,linkTo(BeerController.class).slash(id).withRel("beer"));
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
        if(b != null) {
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
