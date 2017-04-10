package fi.tamk.beerbros.kaljakaupparedo.components;

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
        resource.add(linkTo(MainController.class).slash("beers").withRel("beers"));
        resource.add(linkTo(MainController.class).slash("beertypes").withRel("beertypes"));
        resource.add(linkTo(MainController.class).slash("countries").withRel("countries"));
        return resource;
    }

}
