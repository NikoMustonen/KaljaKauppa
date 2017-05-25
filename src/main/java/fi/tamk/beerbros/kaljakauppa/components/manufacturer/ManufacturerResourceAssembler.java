package fi.tamk.beerbros.kaljakauppa.components.manufacturer;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

/**
 * Class for generating HATEOAS links to manufacturer resources.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Component
public class ManufacturerResourceAssembler
        implements ResourceAssembler<Manufacturer, Resource<Manufacturer>> {

    /**
     * Generates HATEOAS links to manufacturer resource.
     *
     * @param t Country entity object.
     * @return Generated Resource with HATEOAS links.
     */
    @Override
    public Resource<Manufacturer> toResource(Manufacturer t) {
        Resource<Manufacturer> resource = new Resource<>(t);
        resource.add(linkTo(ManufacturerController.class).
                slash(t.getName()).slash("beers").withRel("beers"));
        resource.add(linkTo(ManufacturerController.class).
                slash(t.getName()).withSelfRel());
        return resource;
    }

}
