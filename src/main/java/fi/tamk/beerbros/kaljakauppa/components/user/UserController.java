package fi.tamk.beerbros.kaljakauppa.components.user;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kaljakauppa/users")
public class UserController {

    @Autowired
    private UserRepository ur;
    
    @Autowired
    private UserResourceAssembler resourceAssembler;
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<User>> getAllUsers() {
        Iterable<User> users = ur.findAll();
        List<Resource<User>> resourceList = new ArrayList<>();
        
        for(User u : users) {
            resourceList.add(resourceAssembler.toResource(u));
        }
        
        return new Resources<>(resourceList, linkTo(UserController.class).withSelfRel());
    }
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<User> getUserById(@PathVariable int id) {
        User u = ur.findOne(id);
        return this.resourceAssembler.toResource(u);
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<User> addUser(@RequestBody User user) {
        
        return this.resourceAssembler.toResource(ur.save(user));
    }
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<User> deleteUserById(@PathVariable int id) {
        User u = ur.findOne(id);
        ur.delete(id);
        return this.resourceAssembler.toResource(u);
    }
}
