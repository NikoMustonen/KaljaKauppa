package fi.tamk.beerbros.kaljakaupparedo.components.manufacturer;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

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
