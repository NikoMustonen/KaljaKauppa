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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Rest controller for beer type.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@RestController
@RequestMapping("/kaljakauppa/beertypes")
public class BeerTypeController {

    /**
     * BeerType table handler.
     */
    @Autowired
    BeerTypeRepository btr;

    /**
     * BeerType HATEOAS link generator.
     */
    @Autowired
    BeerTypeResourceAssembler resourceAssembler;

    /**
     * Beer database table handler.
     */
    @Autowired
    BeerRepository br;

    /**
     * Beer HATEOAS link generator.
     */
    @Autowired
    BeerResourceAssembler beerResourceAssembler;

    /**
     * Country HATEOAS link generator.
     */
    @Autowired
    CountryResourceAssembler cra;

    /**
     * Returns all beer type from the database.
     *
     * @return List of beer types.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<BeerType>> getAllBeerTypes() {

        List<BeerType> beerTypes = (List) btr.findAll();

        List<Resource<BeerType>> beerTypeResourceList = new ArrayList<>();

        for (BeerType b : beerTypes) {
            beerTypeResourceList.add(this.resourceAssembler.toResource(b));
        }

        return new Resources<>(beerTypeResourceList,
                linkTo(BeerTypeController.class).withSelfRel());
    }

    /**
     * Returns specific beer type defined by the http request path variable..
     *
     * @param name Chosen name.
     * @return BeerType entity.
     */
    @RequestMapping(
            value = "/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<BeerType> getBeerTypeByName(@PathVariable String name) {
        BeerType beerType = this.btr.findOne(name);
        return this.resourceAssembler.toResource(beerType);
    }

    /**
     * Add new beer type to database.
     *
     * @param beerType BeerType entity which must be sent in JSON format.
     * @return Created BeerType entity object.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<BeerType> addBeerType(@RequestBody BeerType beerType) {
        BeerType b = btr.save(beerType);
        return this.resourceAssembler.toResource(b);
    }

    /**
     * Returns list of beer types by country.
     *
     * @param beerType BeerType object.
     * @return List of beer types under this country.
     */
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

        for (Beer b : beers) {
            b.setReviews(null);
            beerResources.add(beerResourceAssembler.toResource(b));
        }

        return new Resources<>(beerResources, linkTo(BeerTypeController.class)
                .slash(beerType).slash("beers").withSelfRel());
    }

    /**
     * Returns a list of countries by chosen beer type.
     *
     * Gets all the countries that have beer under chosen beer type.
     *
     * @param beerType
     * @return
     */
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

        for (Country c : countries) {
            countryResources.add(cra.toResource(c));
        }

        return new Resources<>(countryResources,
                linkTo(BeerTypeController.class).slash(beerType).
                        slash("countries").withSelfRel());
    }

    /**
     * Returns desired beer type by country and name.
     * 
     * @param countryName Country name in string format.
     * @param beerTypeName Beer type name in string format.
     * @return Country entity object.
     */
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

    /**
     * Returns all beers by country and beer type.
     * 
     * @param countryName Country name in string format.
     * @param beerTypeName Beer type name in String format.
     * @return List of beers.
     */
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

        for (Beer b : beers) {
            b.setReviews(null);
            beerResourceList.add(beerResourceAssembler.toResource(b));
        }

        return new Resources<>(beerResourceList, linkTo(BeerController.class).withSelfRel());
    }

    /**
     * Returns specific beer by country and id.
     * 
     * @param beerType Beer type name in String format.
     * @param id Beer id.
     * @return Beer entity object.
     */
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
