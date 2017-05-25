package fi.tamk.beerbros.kaljakauppa.components.manufacturer;

import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import fi.tamk.beerbros.kaljakauppa.components.beer.BeerController;
import fi.tamk.beerbros.kaljakauppa.components.beer.BeerResourceAssembler;
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
@RequestMapping("/kaljakauppa/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerRepository mr;
    
    @Autowired
    private ManufacturerResourceAssembler resourceAssembler;
    
    @Autowired
    private BeerResourceAssembler beerResourceAssembler;
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<Manufacturer>> getAllManufacturers() {
        Iterable<Manufacturer> manufacturers = mr.findAll();
        List<Resource<Manufacturer>> resourceList = new ArrayList<>();
        
        for(Manufacturer m : manufacturers) {
            resourceList.add(resourceAssembler.toResource(m));
        }
        
        return new Resources<>(resourceList, linkTo(ManufacturerController.class).withSelfRel());
    }
    
    @RequestMapping(
            value = "/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Manufacturer> getManufacturerByName(@PathVariable String name) {
        Manufacturer m = mr.findOne(name);
        return this.resourceAssembler.toResource(m);
    }
    
    @RequestMapping(
            value = "/{name}/beers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<Beer>> getBeers(@PathVariable String name) {
        Manufacturer m = mr.findOne(name);
        Iterable<Beer> beers = mr.findAllBeerByManufacturer(m);
        List<Resource<Beer>> beerResourceList = new ArrayList<>();
        
        for(Beer b : beers) {
            b.setReviews(null);
            beerResourceList.add(beerResourceAssembler.toResource(b));
        }
        
        return new Resources<>(beerResourceList, linkTo(BeerController.class).withSelfRel());
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<Manufacturer> addManufacturer(@RequestBody Manufacturer manufacturer) {
        Manufacturer m = mr.save(manufacturer);
        return this.resourceAssembler.toResource(m);
    }
}
