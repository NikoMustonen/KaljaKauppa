package fi.tamk.beerbros.kaljakauppa.components;

import fi.tamk.beerbros.kaljakauppa.components.beer.BeerController;
import fi.tamk.beerbros.kaljakauppa.components.beertype.BeerTypeController;
import fi.tamk.beerbros.kaljakauppa.components.manufacturer.ManufacturerController;
import fi.tamk.beerbros.kaljakauppa.components.country.CountryController;
import fi.tamk.beerbros.kaljakauppa.components.review.ReviewController;
import fi.tamk.beerbros.kaljakauppa.components.user.UserController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

@Component
public class MainResourceAssembler
        implements ResourceAssembler<Object, Resource<Object>> {

    @Override
    public Resource<Object> toResource(Object t) {
        Resource<Object> resource = new Resource<>(t);
        resource.add(linkTo(BeerController.class).withRel("beers"));
        resource.add(linkTo(BeerController.class).slash("?start=" + 1 + "&step=" + 10).withRel("tenFirst"));
        resource.add(linkTo(BeerTypeController.class).withRel("beertypes"));
        resource.add(linkTo(CountryController.class).withRel("countries"));
        resource.add(linkTo(ManufacturerController.class).withRel("manufacturers"));
        resource.add(linkTo(UserController.class).withRel("users"));
        resource.add(linkTo(ReviewController.class).withRel("reviews"));
        return resource;
    }
}
