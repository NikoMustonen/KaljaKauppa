package fi.tamk.beerbros.kaljakauppa.components.review;

import fi.tamk.beerbros.kaljakauppa.components.MainResourceAssembler;
import fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions.BadRequestException;
import fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions.NotFoundException;
import fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions.ReviewedAlreadyException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

        for (Review r : reviews) {
            r.getBeer().setReviews(null);
            resourceList.add(resourceAssembler.toResource(r));
        }
        Resources<Resource<Review>> res = new Resources<>(resourceList, linkTo(ReviewController.class).withSelfRel());
        MainResourceAssembler.addLinksToResources(res);
        
        return res;
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
    public Resource addReview(@RequestBody Review review) throws ReviewedAlreadyException, BadRequestException {
        try {
            Review r = review;
            if (rr.findOneByUserAndBeer(r.getUser(), r.getBeer()) != null) {
                throw new ReviewedAlreadyException();
            }
            r = rr.save(review);
            Review returnedReview = rr.findOne(r.getId());
            return this.resourceAssembler.toResource(returnedReview);
        } catch (ReviewedAlreadyException e) {
            throw e;
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Error while adding review");
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Review> modifyReviewById(
            @PathVariable Long id, @RequestBody Review review)
            throws BadRequestException, NotFoundException {

        try {
            Review r = rr.findOne(id);
            try {
                r.setRating(review.getRating());
                r.setTitle(review.getTitle());
                r.setText(review.getText());
                r = rr.save(r);
                r.getBeer().setReviews(null);
                return this.resourceAssembler.toResource(r);
            } catch (Exception e) {
                throw new BadRequestException("Review is in wrong format");
            }
        } catch (Exception e) {
            throw new NotFoundException("Cannot find review with given id");
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Review> deleteUserById(@PathVariable Long id)
            throws NotFoundException {
        try {
            Review r = rr.findOne(id);
            rr.delete(id);
            return this.resourceAssembler.toResource(r);
        } catch (Exception e) {
            throw new NotFoundException("No review with given id");
        }
    }
}
