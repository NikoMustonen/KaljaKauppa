package fi.tamk.beerbros.kaljakauppa.components.user;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @Autowired
    UserRepository ur;
    
    @RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(ur.findAll());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        try {
            ur.save(user);
            URI uri = URI.create("users/" + user.getId());
            return ResponseEntity.created(uri).body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
