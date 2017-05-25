package fi.tamk.beerbros.kaljakauppa.components.beertype;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
@Component
public final class BeerTypeResourceAssembler
        implements ResourceAssembler<BeerType, Resource<BeerType>> {

    @Override
    public Resource<BeerType> toResource(BeerType t) {
        Resource<BeerType> resource = new Resource<>(t);
        resource.add(linkTo(BeerTypeController.class).slash(t.getName()).slash("beers").withRel("beers"));
        resource.add(linkTo(BeerTypeController.class).slash(t.getName()).slash("countries").withRel("countries"));
        resource.add(linkTo(BeerTypeController.class).slash(t.getName()).withSelfRel());
        return resource;
    }

}
