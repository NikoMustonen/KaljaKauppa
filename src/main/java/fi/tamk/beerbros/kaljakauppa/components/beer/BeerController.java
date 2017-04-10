package fi.tamk.beerbros.kaljakauppa.components.beer;

import java.sql.Timestamp;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Beer> modifyBeer(
            @PathVariable int id, 
            @RequestBody Beer beer) {
        Beer b = br.findOne(id);
        b = beer;
        return resourceAssembler.toResource(br.save(b));
    }
}
