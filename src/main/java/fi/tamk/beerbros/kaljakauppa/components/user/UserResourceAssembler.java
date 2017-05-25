package fi.tamk.beerbros.kaljakauppa.components.user;

import fi.tamk.beerbros.kaljakauppa.components.shoppingcart.ShoppingCart;
import fi.tamk.beerbros.kaljakauppa.components.shoppingcart.ShoppingCartController;
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
        resource.add(linkTo(ShoppingCartController.class).slash(u.getId()).slash("shoppingcart").withRel("shoppingcart"));
        return resource;
    }
}
