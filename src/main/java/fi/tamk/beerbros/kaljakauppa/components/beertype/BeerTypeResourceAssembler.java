package fi.tamk.beerbros.kaljakauppa.components.beertype;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

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
