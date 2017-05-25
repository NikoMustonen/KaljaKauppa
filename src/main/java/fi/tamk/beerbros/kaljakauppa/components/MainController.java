package fi.tamk.beerbros.kaljakauppa.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller class for main page links.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@RestController
public class MainController {
    
    /**
     * Link generator for main page.
     */
    @Autowired
    MainResourceAssembler resourceAssembler;
    
    /**
     * Generates and returns HATEOAS links to different resource.
     * 
     * @return 
     */
    @RequestMapping(
            value = "/kaljakauppa",
            method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource getMainPage() {
        return resourceAssembler.toResource("Main page");
    }
}
