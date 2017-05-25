package fi.tamk.beerbros.kaljakauppa.components;

import fi.tamk.beerbros.kaljakauppa.components.beer.BeerController;
import fi.tamk.beerbros.kaljakauppa.components.beertype.BeerTypeController;
import fi.tamk.beerbros.kaljakauppa.components.manufacturer.*;
import fi.tamk.beerbros.kaljakauppa.components.country.CountryController;
import fi.tamk.beerbros.kaljakauppa.components.review.ReviewController;
import fi.tamk.beerbros.kaljakauppa.components.user.UserController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

/**
 * Class for generating HATEOAS links to applications main resources.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Component
public class MainResourceAssembler
        implements ResourceAssembler<Object, Resource<Object>> {

    /**
     * Generates Resource object with HATEOAS links to applications resources.
     *
     * @param t Object to be resourcesized.
     * @return Generated resource object.
     */
    @Override
    public Resource<Object> toResource(Object t) {
        Resource<Object> resource = new Resource<>(t);
        resource.add(linkTo(BeerController.class).withRel("beers"));
        resource.add(linkTo(BeerController.class).slash("?start=" + 1
                + "&step=" + 10).withRel("tenFirstBeers"));
        resource.add(linkTo(BeerTypeController.class).withRel("beertypes"));
        resource.add(linkTo(CountryController.class).withRel("countries"));
        resource.add(linkTo(ManufacturerController.class).
                withRel("manufacturers"));
        resource.add(linkTo(UserController.class).withRel("users"));
        resource.add(linkTo(ReviewController.class).withRel("reviews"));
        return resource;
    }

    /**
     * Adds main HATEOAS links to Resource object.
     *
     * @param resource Resource where links will be added.
     */
    public static void addLinksToResource(Resource resource) {
        resource.add(linkTo(BeerController.class).withRel("beers"));
        resource.add(linkTo(BeerController.class).slash("?start=" + 1
                + "&step=" + 10).withRel("tenFirstBeers"));
        resource.add(linkTo(BeerTypeController.class).withRel("beertypes"));
        resource.add(linkTo(CountryController.class).withRel("countries"));
        resource.add(linkTo(ManufacturerController.class).
                withRel("manufacturers"));
        resource.add(linkTo(ReviewController.class).withRel("reviews"));
    }

    /**
     * Adds main HATEOAS links to Resources object.
     *
     * @param resources Resources where links will be added.
     */
    public static void addLinksToResources(Resources resources) {
        resources.add(linkTo(BeerController.class).withRel("beers"));
        resources.add(linkTo(BeerController.class).slash("?start=" + 1
                + "&step=" + 10).withRel("tenFirstBeers"));
        resources.add(linkTo(BeerTypeController.class).withRel("beertypes"));
        resources.add(linkTo(CountryController.class).withRel("countries"));
        resources.add(linkTo(ManufacturerController.class).
                withRel("manufacturers"));
        resources.add(linkTo(ReviewController.class).withRel("reviews"));
    }
}
