package fi.tamk.beerbros.kaljakauppa.components.country;

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
public class CountryResourceAssembler 
        implements ResourceAssembler<Country, Resource<Country>>{

    @Override
    public Resource<Country> toResource(Country t) {
        Resource<Country> resource = new Resource<>(t);
        resource.add(linkTo(CountryController.class).slash(t.getName()).slash("beers").withRel("beers"));
        resource.add(linkTo(CountryController.class).slash(t.getName()).slash("beertypes").withRel("beertypes"));
        resource.add(linkTo(CountryController.class).slash(t.getName()).withSelfRel());
        return resource;
    }
    
}
