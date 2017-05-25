package fi.tamk.beerbros.kaljakauppa.components.review;

import fi.tamk.beerbros.kaljakauppa.components.beer.BeerController;
import fi.tamk.beerbros.kaljakauppa.components.user.UserController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

/**
 * Class for generating HATEOAS links to review resources.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Component
public class ReviewResourceAssembler
        implements ResourceAssembler<Review, Resource<Review>> {

    /**
     * Generates HATEOAS links to review resource.
     *
     * @param r Review entity object.
     * @return Generated Resource with HATEOAS links.
     */
    @Override
    public Resource<Review> toResource(Review r) {
        Resource<Review> resource = new Resource<>(r);
        resource.add(linkTo(ReviewController.class).slash(r.getId()).
                withSelfRel());
        resource.add(linkTo(BeerController.class).slash(r.getBeer().
                getId()).slash("reviews").withRel("reviewsOfCurrentBeer"));
        resource.add(linkTo(ReviewController.class).withRel("reviews"));
        resource.add(linkTo(BeerController.class).slash(r.getBeer().
                getId()).withRel("beer"));
        resource.add(linkTo(UserController.class).slash(r.getUser().
                getId()).withRel("user"));
        return resource;
    }

}
