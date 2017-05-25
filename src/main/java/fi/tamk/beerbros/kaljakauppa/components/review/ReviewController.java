package fi.tamk.beerbros.kaljakauppa.components.review;

import fi.tamk.beerbros.kaljakauppa.components.MainResourceAssembler;
import fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions.*;

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

/**
 * Rest controller class for review management.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@RestController
@RequestMapping("/kaljakauppa/reviews")
public class ReviewController {

    /**
     * Review database handler.
     */
    @Autowired
    private ReviewRepository rr;

    /**
     * HATEOAS link generator for review resources.
     */
    @Autowired
    private ReviewResourceAssembler resourceAssembler;

    /**
     * Returns all reviews from the database.
     *
     * @return List of reviews.
     */
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
        Resources<Resource<Review>> res = new Resources<>(resourceList,
                linkTo(ReviewController.class).withSelfRel());
        MainResourceAssembler.addLinksToResources(res);

        return res;
    }

    /**
     * Returns specific review by given id.
     *
     * @param id Id number.
     * @return Review entity object.
     */
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

    /**
     * Adds review to database.
     *
     * @param review Review to be added.
     * @return Added review.
     * @throws ReviewedAlreadyException If duplicate entry.
     * @throws BadRequestException If bad request.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource addReview(@RequestBody Review review)
            throws ReviewedAlreadyException, BadRequestException {
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

    /**
     * Modifies review by given id and data.
     *
     * @param id Review id.
     * @param review New data.
     * @return Modified review.
     * @throws BadRequestException if request was bad.
     * @throws NotFoundException If resource was not found.
     */
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

    /**
     * Deletes review by given id.
     *
     * @param id Review id.
     * @return Removed review.
     * @throws NotFoundException If review was not found.
     */
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
