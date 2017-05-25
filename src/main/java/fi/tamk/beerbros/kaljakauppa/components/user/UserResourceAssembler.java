package fi.tamk.beerbros.kaljakauppa.components.user;

import fi.tamk.beerbros.kaljakauppa.components.shoppingcart.ShoppingCartController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

/**
 * Generates User resources with HATEOAS links.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Component
public class UserResourceAssembler 
        implements ResourceAssembler<User, Resource<User>>{

    /**
     * Generates User resource with HATEOAS links.
     * 
     * @param u User to be generated.
     * @return Resource with HATEOS links.
     */
    @Override
    public Resource<User> toResource(User u) {
        Resource<User> resource = new Resource<>(u);
        resource.add(linkTo(UserController.class).slash(u.getId()).withSelfRel());
        resource.add(linkTo(ShoppingCartController.class).slash(u.getId()).slash("shoppingcart").withRel("shoppingcart"));
        return resource;
    }
}
