package fi.tamk.beerbros.kaljakaupparedo.components.beer;

import java.sql.Timestamp;
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
@RequestMapping("/beers")
public class BeerController {

    @Autowired
    private BeerRepository br;
    
    @Autowired
    BeerResourceAssembler resourceAssembler;
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Resource<Beer>> getBeers() {
        
        Iterable<Beer> beers = br.findAll();
        List<Resource<Beer>> beerResourceList = new ArrayList<>();
        
        for(Beer b : beers) {
            beerResourceList.add(resourceAssembler.toResource(b));
        }
        
        return new Resources<>(beerResourceList, linkTo(BeerController.class).withSelfRel());
    }
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Beer> getBeer(@PathVariable int id) {
        
        Beer b = br.findOne(id);
        
        return this.resourceAssembler.toResource(b);
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addBeer(@RequestBody Beer beer) {
        beer.setTimeAdded(new Timestamp(System.currentTimeMillis()));
        br.save(beer);
    }
}
