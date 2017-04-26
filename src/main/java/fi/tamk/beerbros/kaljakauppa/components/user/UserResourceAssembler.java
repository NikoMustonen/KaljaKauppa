package fi.tamk.beerbros.kaljakauppa.components.user;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAssembler 
        implements ResourceAssembler<User, Resource<User>>{

    @Override
    public Resource<User> toResource(User u) {
        Resource<User> resource = new Resource<>(u);
        resource.add(linkTo(UserController.class).slash(u.getId()).withSelfRel());
        return resource;
    }
}
