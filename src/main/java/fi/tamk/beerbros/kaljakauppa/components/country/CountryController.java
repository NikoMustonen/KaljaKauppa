package fi.tamk.beerbros.kaljakaupparedo.components.country;

import fi.tamk.beerbros.kaljakaupparedo.components.beer.Beer;
import fi.tamk.beerbros.kaljakaupparedo.components.beer.BeerRepository;
import fi.tamk.beerbros.kaljakaupparedo.components.beer.BeerResourceAssembler;
import fi.tamk.beerbros.kaljakaupparedo.components.beertype.BeerType;
import fi.tamk.beerbros.kaljakaupparedo.components.beertype.BeerTypeResourceAssembler;
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
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryRepository cr;
    
    @Autowired
    private BeerRepository br;
    
    @Autowired
    private CountryResourceAssembler resourceAssembler;
    
    @Autowired
    private BeerTypeResourceAssembler beerTypeResourceAssembler;
    
    @Autowired
    private BeerResourceAssembler beerResourceAssembler;
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<Country>> getAllCountries() {
        Iterable<Country> countries = cr.findAll();
        List<Resource<Country>> resourceList = new ArrayList<>();
        
        for(Country c : countries) {
            resourceList.add(resourceAssembler.toResource(c));
        }
        
        return new Resources<>(resourceList, linkTo(CountryController.class).withSelfRel());
    }
    
    @RequestMapping(
            value = "/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Country> getCountryByName(@PathVariable String name) {
        Country c = cr.findOne(name);
        return this.resourceAssembler.toResource(c);
    }
    
    @RequestMapping(
            value = "/{countryName}/beertypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<BeerType>> getBeerTypesByCountry(@PathVariable String countryName) {
        Iterable<BeerType> beerTypes = cr.findAllBeerTypesByCountryId(new Country(countryName));
        List<Resource<BeerType>> resources = new ArrayList<>();
        
        for(BeerType b : beerTypes) {
            resources.add(beerTypeResourceAssembler.toResource(b));
        }
        
        return new Resources<>(resources, linkTo(CountryController.class).slash(countryName).slash("beertypes").withSelfRel());
    }
    
    @RequestMapping(
            value = "/{countryName}/beertypes/{beerTypeName}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<BeerType> getBeerTypeByCountry(
            @PathVariable String countryName,
            @PathVariable String beerTypeName) {
        
        BeerType b = cr.findBeerTypeByCountryAndType(
                new Country(countryName), 
                new BeerType(beerTypeName));
        
        Resource<BeerType> resource = beerTypeResourceAssembler.toResource(b);
        return resource;
    }
    
    @RequestMapping(
            value = "/{countryName}/beers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<Beer>> getBeersByCountry(
            @PathVariable String countryName) {
        
        List<Beer> beers = br.findByCountry(new Country(countryName));
        List<Resource<Beer>> beerResources = new ArrayList<>();
        
        for(Beer b : beers) {
            beerResources.add(beerResourceAssembler.toResource(b));
        }
        
        return new Resources<>(beerResources, linkTo(CountryController.class).slash(countryName).slash("beers").withSelfRel());
    }
    
    @RequestMapping(
            value = "/{countryName}/beers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Beer> getBeerByCountry(
            @PathVariable String countryName,
            @PathVariable int id) {
        
        Beer b = br.findOne(id);
        
        return this.beerResourceAssembler.toResource(b);
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<Country> addCountry(@RequestBody Country country) {
        Country m = cr.save(country);
        return this.resourceAssembler.toResource(m);
    }
}