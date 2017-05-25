package fi.tamk.beerbros.kaljakauppa.components.beertype;

import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import fi.tamk.beerbros.kaljakauppa.components.beer.BeerController;
import fi.tamk.beerbros.kaljakauppa.components.beer.BeerRepository;
import fi.tamk.beerbros.kaljakauppa.components.beer.BeerResourceAssembler;
import fi.tamk.beerbros.kaljakauppa.components.country.Country;
import fi.tamk.beerbros.kaljakauppa.components.country.CountryResourceAssembler;
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

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
@RestController
@RequestMapping("/kaljakauppa/beertypes")
public class BeerTypeController {
    
    @Autowired
    BeerTypeRepository btr;
    
    @Autowired
    BeerTypeResourceAssembler resourceAssembler;
    
    @Autowired
    BeerRepository br;
    
    @Autowired
    BeerResourceAssembler beerResourceAssembler;
    
    @Autowired
    CountryResourceAssembler cra;
    
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
            b.setReviews(null);
            beerResources.add(beerResourceAssembler.toResource(b));
        }
        
        return new Resources<>(beerResources, linkTo(BeerTypeController.class).slash(beerType).slash("beers").withSelfRel());
    }
    
    @RequestMapping(
            value = "/{beerType}/countries",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<Country>> getCountriesByBeerType(
            @PathVariable String beerType) {
        
        BeerType bt = btr.findOne(beerType);
        Iterable<Country> countries = btr.findAllCountriesByBeerType(bt);
        List<Resource<Country>> countryResources = new ArrayList<>();
        
        for(Country c : countries) {
            countryResources.add(cra.toResource(c));
        }
        
        return new Resources<>(countryResources, linkTo(BeerTypeController.class).slash(beerType).slash("countries").withSelfRel());
    }
    
    @RequestMapping(
            value = "/{beerTypeName}/countries/{countryName}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Country> getBeerTypeByCountry(
            @PathVariable String countryName,
            @PathVariable String beerTypeName) {
        
        Country c = btr.findCountryByCountryAndType(
                new Country(countryName), 
                new BeerType(beerTypeName));
        
        Resource<Country> resource = cra.toResource(c);
        return resource;
    }
    
    @RequestMapping(
            value = "/{beerTypeName}/countries/{countryName}/beers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<Beer>> getBeersByBeerTypeAndCountry(
            @PathVariable String countryName,
            @PathVariable String beerTypeName) {
        
        BeerType bt = new BeerType(beerTypeName);
        Country ct = new Country(countryName);
        
        Iterable<Beer> beers = btr.findAllBeerByBeerTypeAndCountry(bt, ct);
        List<Resource<Beer>> beerResourceList = new ArrayList<>();
        
        for(Beer b : beers) {
            b.setReviews(null);
            beerResourceList.add(beerResourceAssembler.toResource(b));
        }
        
        return new Resources<>(beerResourceList, linkTo(BeerController.class).withSelfRel());
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
