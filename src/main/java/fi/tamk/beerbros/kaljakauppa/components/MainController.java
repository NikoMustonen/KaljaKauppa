package fi.tamk.beerbros.kaljakauppa.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
@RestController
public class MainController {
    
    @Autowired
    MainResourceAssembler resourceAssembler;
    
    @RequestMapping(
            value = "/kaljakauppa",
            method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource getMainPage() {
        return resourceAssembler.toResource("Main page");
    }
}
