package fi.tamk.beerbros.kaljakaupparedo.components.beer;

import fi.tamk.beerbros.kaljakaupparedo.components.beertype.BeerTypeController;
import fi.tamk.beerbros.kaljakaupparedo.components.country.CountryController;
import fi.tamk.beerbros.kaljakaupparedo.components.manufacturer.ManufacturerController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

@Component
public class BeerResourceAssembler implements
        ResourceAssembler<Beer, Resource<Beer>> {

    @Override
    public Resource<Beer> toResource(Beer beer) {
        Resource<Beer> resource = new Resource<>(beer);
        
        resource.add(linkTo(ManufacturerController.class).slash(beer.getManufacturer().getName()).withRel("manufacturer"));
        resource.add(linkTo(CountryController.class).slash(beer.getCountry().getName()).withRel("country"));
        resource.add(linkTo(BeerTypeController.class).slash(beer.getBeerType().getName()).withRel("beerType"));
        resource.add(linkTo(BeerController.class).slash(beer.getId()).slash("reviews").withRel("reviews"));
        resource.add(linkTo(BeerController.class).slash(beer.getId()).withSelfRel());
        return resource;
    }
}
