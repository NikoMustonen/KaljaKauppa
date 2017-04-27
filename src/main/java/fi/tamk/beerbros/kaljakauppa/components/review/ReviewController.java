package fi.tamk.beerbros.kaljakauppa.components.review;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/kaljakauppa/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository rr;
    
    @Autowired
    private ReviewResourceAssembler resourceAssembler;
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<Review>> getAllReviews() {
        Iterable<Review> reviews = rr.findAll();
        List<Resource<Review>> resourceList = new ArrayList<>();
        
        for(Review r : reviews) {
            r.getBeer().setReviews(null);
            resourceList.add(resourceAssembler.toResource(r));
        }
        
        return new Resources<>(resourceList, linkTo(ReviewController.class).withSelfRel());
    }
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Review> getReviewById(@PathVariable Long id) {
        Review r = rr.findOne(id);
        r.getBeer().setReviews(null);
        return this.resourceAssembler.toResource(r);
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<Review> addReview(@RequestBody Review review) {
        Review r = rr.save(review);
        Review returnedReview = rr.findOne(r.getId());
        return this.resourceAssembler.toResource(returnedReview);
    }
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Review> modifyReviewById(@PathVariable Long id, @RequestBody Review review) {
        
        Review r = rr.findOne(id);
        r.setRating(review.getRating());
        r.setTitle(review.getTitle());
        r.setText(review.getText());
        r = rr.save(r);
        
        return this.resourceAssembler.toResource(r);
    }
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Review> deleteUserById(@PathVariable Long id) {
        Review r = rr.findOne(id);
        rr.delete(id);
        return this.resourceAssembler.toResource(r);
    }
}
