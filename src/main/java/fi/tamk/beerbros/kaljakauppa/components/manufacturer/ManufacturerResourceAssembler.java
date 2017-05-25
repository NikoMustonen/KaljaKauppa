package fi.tamk.beerbros.kaljakauppa.components.manufacturer;

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
public class ManufacturerResourceAssembler 
        implements ResourceAssembler<Manufacturer, Resource<Manufacturer>>{

    @Override
    public Resource<Manufacturer> toResource(Manufacturer t) {
        Resource<Manufacturer> resource = new Resource<>(t);
        resource.add(linkTo(ManufacturerController.class).slash(t.getName()).slash("beers").withRel("beers"));
        resource.add(linkTo(ManufacturerController.class).slash(t.getName()).withSelfRel());
        return resource;
    }
    
}
