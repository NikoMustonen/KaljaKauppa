package fi.tamk.beerbros.kaljakauppa.components.shoppingcart;

import fi.tamk.beerbros.kaljakauppa.components.beer.BeerController;
import fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions.NotFoundException;
import fi.tamk.beerbros.kaljakauppa.components.user.User;
import fi.tamk.beerbros.kaljakauppa.components.user.UserController;
import fi.tamk.beerbros.kaljakauppa.components.user.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
@RestController
@RequestMapping(value = "/kaljakauppa/users")
public class ShoppingCartController {

    @Autowired
    ShoppingCartRepository scr;

    @Autowired
    UserRepository ur;

    @Autowired
    OrderRepository or;

    @Autowired
    ShoppingItemRepository sir;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}/shoppingcart"
    )
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<ShoppingCartItem>> getShoppingCart(
            @PathVariable int id
    ) throws NotFoundException {
        ShoppingCart shoppingCart = getCart(id);
        Resources<Resource<ShoppingCartItem>> resources;
        List<Resource<ShoppingCartItem>> itemResourceList = new ArrayList<>();

        for (ShoppingCartItem i : shoppingCart.getItems()) {
            i.setShoppingCartVisible(false);
            Resource<ShoppingCartItem> resource = new Resource<>(i);
            resource.add(linkTo(BeerController.class).slash(i.getBeer()).withSelfRel());
            itemResourceList.add(resource);
        }
        resources = new Resources<>(itemResourceList);
        resources.add(linkTo(UserController.class).slash(id).withRel("user"));
        resources.add(linkTo(ShoppingCartController.class).slash(id).slash("shoppingcart").withSelfRel());

        return resources;
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}/shoppingcart"
    )
    @ResponseStatus(HttpStatus.OK)
    public Resource<ShoppingCart> addItemToCart(@PathVariable Integer id, @RequestBody ShoppingCartItem item) throws NotFoundException {
        ShoppingCart shoppingCart = getCart(id);

        ShoppingCartItem tmpItem = shoppingCart.checkIfHasItemAndGetIt(item);

        if (tmpItem != null) {
            tmpItem.setAmount(tmpItem.getAmount() + item.getAmount());
            item = tmpItem;
        } else {
            shoppingCart.getItems().add(item);
        }

        if (item.getAmount() < 1) {
            sir.delete(item);
        } else {
            sir.save(item);
            scr.save(shoppingCart);
            for (ShoppingCartItem i : shoppingCart.getItems()) {
                i.setShoppingCartVisible(false);
            }

            Resource<ShoppingCart> res = new Resource<>(shoppingCart);
            res.add(linkTo(UserController.class).slash(id).withRel("user"));
            res.add(linkTo(BeerController.class).slash(item.getBeer()).withRel("beer"));
            res.add(linkTo(ShoppingCartController.class).slash(id).slash("shoppingcart").withSelfRel());
            return res;
        }
        Resource<ShoppingCart> res = new Resource<>(shoppingCart);
        res.add(linkTo(UserController.class).slash(id).withRel("user"));
        res.add(linkTo(ShoppingCartController.class).slash(id).slash("shoppingcart").withSelfRel());
        return res;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}/shoppingcart"
    )
    @ResponseStatus(HttpStatus.OK)
    public Resources placeOrder(@PathVariable Integer id) throws NotFoundException {
        ShoppingCart shoppingCart = scr.findOne(id);
        if (shoppingCart == null || shoppingCart.getItems().size() < 1) {
            throw new NotFoundException("Shopping cart was empty");
        }

        ArrayList<Resource> resItems = new ArrayList<>();

        Collection<ShoppingCartItem> items = shoppingCart.getItems();
        Order o = new Order();
        User u = new User(id);
        o.setUser(u);
        o = or.save(o);

        shoppingCart.setItems(new ArrayList<>());
        for (ShoppingCartItem i : items) {
            i.setShoppingCart(null);
            i.setOrder(o);
            sir.save(i);
            Resource<ShoppingCartItem> resI = new Resource<>(i);
            resI.add(linkTo(BeerController.class).slash(i.getBeer()).withSelfRel());
            resItems.add(resI);
        }
        scr.save(shoppingCart);
        o.setItems(items);
        o = or.save(o);

        if (items != null) {
            System.out.println("items: " + items.size());
        }
        Resource<User> resU = new Resource<>(o.getUser());
        resU.add(linkTo(UserController.class).slash(o.getUser().getId()).withSelfRel());
        resItems.add(resU);

        Resources allResources = new Resources(resItems);

        return allResources;
    }

    private ShoppingCart getCart(int userId) throws NotFoundException {
        try {
            User u = ur.findOne(userId);
            if (u == null) {
                throw new Exception();
            }
            ShoppingCart sc = scr.findOne(userId);
            if (sc == null) {
                sc = new ShoppingCart(userId);
                sc.setItems(new ArrayList<>());
                return scr.save(sc);
            }
            return sc;
        } catch (Exception e) {
            throw new NotFoundException("Could not find user!");
        }
    }
}
