package fi.tamk.beerbros.kaljakauppa.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {
    
    @Autowired
    MainResourceAssembler resourceAssembler;
    
    @RequestMapping(
            method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource getMainPage() {
        return resourceAssembler.toResource("Main page");
    }
}
