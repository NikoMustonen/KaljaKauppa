package fi.tamk.beerbros.kaljakauppa.components.review;

import fi.tamk.beerbros.kaljakauppa.components.beer.BeerController;
import fi.tamk.beerbros.kaljakauppa.components.user.UserController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

@Component
public class ReviewResourceAssembler 
        implements ResourceAssembler<Review, Resource<Review>>{

    @Override
    public Resource<Review> toResource(Review r) {
        Resource<Review> resource = new Resource<>(r);
        resource.add(linkTo(BeerController.class).slash(r.getBeer().getId()).slash("reviews").slash(r.getId()).withSelfRel());
        resource.add(linkTo(BeerController.class).slash(r.getBeer().getId()).slash("reviews").withRel("reviewsOfCurrentBeer"));
        resource.add(linkTo(ReviewController.class).withRel("reviews"));
        resource.add(linkTo(BeerController.class).slash(r.getBeer().getId()).withRel("beer"));
        resource.add(linkTo(UserController.class).slash(r.getUser().getId()).withRel("user"));
        return resource;
    }
    
}
