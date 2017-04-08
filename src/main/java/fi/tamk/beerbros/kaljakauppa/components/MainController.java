package fi.tamk.beerbros.kaljakauppa.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    public static final String BEERS = "beers";
    public static final String COUNTRIES = "countries";
    public static final String BEERTYPES = "beertypes";
    
    private final Link[] linksArray = {
        new Link(BEERS),
        new Link(COUNTRIES),
        new Link(BEERTYPES)
    };
    private final Links links = new Links(linksArray);
    private final String JSON_LINKS;

    public MainController() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        JSON_LINKS = ow.writeValueAsString(links);
    }

    @RequestMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> getMainPageInfo() {

        return ResponseEntity.ok(JSON_LINKS);
    }
}
