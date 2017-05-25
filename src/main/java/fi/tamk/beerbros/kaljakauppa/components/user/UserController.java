package fi.tamk.beerbros.kaljakauppa.components.user;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Rest controller for user handling.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@RestController
@RequestMapping("/kaljakauppa/users")
public class UserController {

    /**
     * User database table handler.
     */
    @Autowired
    private UserRepository ur;

    /**
     * User resource HATEOAS link generator.
     */
    @Autowired
    private UserResourceAssembler resourceAssembler;

    /**
     * Returns all the users from the database.
     *
     * @return List of all users.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<User>> getAllUsers() {
        Iterable<User> users = ur.findAll();
        List<Resource<User>> resourceList = new ArrayList<>();

        for (User u : users) {
            resourceList.add(resourceAssembler.toResource(u));
        }

        return new Resources<>(resourceList, linkTo(UserController.class).withSelfRel());
    }

    /**
     * Returns specific user with given id.
     *
     * @param id Given id.
     * @return User entity object.
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<User> getUserById(@PathVariable int id) {
        User u = ur.findOne(id);
        return this.resourceAssembler.toResource(u);
    }

    /**
     * Adds user to database.
     *
     * @param user User entity object to be added.
     * @return Created user entity object.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<User> addUser(@RequestBody User user) {

        return this.resourceAssembler.toResource(ur.save(user));
    }

    /**
     * Deletes user by given id.
     *
     * @param id Given id.
     * @return Deleted user entity object.
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<User> deleteUserById(@PathVariable int id) {
        User u = ur.findOne(id);
        ur.delete(id);
        return this.resourceAssembler.toResource(u);
    }

    /**
     * Modifies user by given id.
     *
     * @param id Given id.
     * @param user New user values.
     * @return Modified user resource.
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<User> modifyUserById(@PathVariable int id, @RequestBody User user) {

        User u = ur.findOne(id);
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setPhone(user.getPhone());
        u.setEmail(user.getEmail());
        u.setStreet(user.getStreet());
        u.setCity(user.getCity());
        u.setAreaCode(user.getAreaCode());
        u = ur.save(u);

        return this.resourceAssembler.toResource(u);
    }
}
