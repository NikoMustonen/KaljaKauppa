package fi.tamk.beerbros.kaljakaupparedo.components.beertype;

import fi.tamk.beerbros.kaljakaupparedo.components.beer.Beer;
import fi.tamk.beerbros.kaljakaupparedo.components.beer.BeerRepository;
import fi.tamk.beerbros.kaljakaupparedo.components.beer.BeerResourceAssembler;
import fi.tamk.beerbros.kaljakaupparedo.components.country.Country;
import fi.tamk.beerbros.kaljakaupparedo.components.country.CountryController;
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
@RequestMapping("/beertypes")
public class BeerTypeController {
    
    @Autowired
    BeerTypeRepository btr;
    
    @Autowired
    BeerTypeResourceAssembler resourceAssembler;
    
    @Autowired
    BeerRepository br;
    
    @Autowired
    BeerResourceAssembler beerResourceAssembler;
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<BeerType>> getAllBeerTypes() {
        
        List<BeerType> beerTypes = (List)btr.findAll();
        
        List<Resource<BeerType>> beerTypeResourceList = new ArrayList<>();
        
        for(BeerType b : beerTypes) {
            beerTypeResourceList.add(this.resourceAssembler.toResource(b));
        }
        
        return new Resources<>(beerTypeResourceList, linkTo(BeerTypeController.class).withSelfRel());
    }
    
    @RequestMapping(
            value = "/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<BeerType> getBeerTypeByName(@PathVariable String name) {
        BeerType beerType = this.btr.findOne(name);
        return this.resourceAssembler.toResource(beerType);
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<BeerType> addBeerType(@RequestBody BeerType beerType) {
        BeerType b = btr.save(beerType);
        return this.resourceAssembler.toResource(b);
    }
    
    @RequestMapping(
            value = "/{beerType}/beers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<Beer>> getBeersByCountry(
            @PathVariable String beerType) {
        
        BeerType bt = btr.findOne(beerType);
        List<Beer> beers = br.findByBeerType(bt);
        List<Resource<Beer>> beerResources = new ArrayList<>();
        
        for(Beer b : beers) {
            beerResources.add(beerResourceAssembler.toResource(b));
        }
        
        return new Resources<>(beerResources, linkTo(BeerTypeController.class).slash(beerType).slash("beers").withSelfRel());
    }
    
    @RequestMapping(
            value = "/{beerType}/beers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Beer> getBeerByCountry(
            @PathVariable String beerType,
            @PathVariable int id) {
        
        Beer b = br.findOne(id);
        
        return this.beerResourceAssembler.toResource(b);
    }
}
