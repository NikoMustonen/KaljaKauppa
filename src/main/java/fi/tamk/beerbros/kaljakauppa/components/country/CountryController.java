package fi.tamk.beerbros.kaljakauppa.components.country;

import fi.tamk.beerbros.kaljakauppa.components.MainResourceAssembler;
import fi.tamk.beerbros.kaljakauppa.components.beer.*;
import fi.tamk.beerbros.kaljakauppa.components.beertype.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Rest controller for Country objects.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@RestController
@RequestMapping("/kaljakauppa/countries")
public class CountryController {

    /**
     * Handles country entities in database.
     */
    @Autowired
    private CountryRepository cr;

    /**
     * Handles beer entities in database.
     */
    @Autowired
    private BeerRepository br;

    /**
     * HATEOAS link generator for country resources.
     */
    @Autowired
    private CountryResourceAssembler resourceAssembler;

    /**
     * HATEOAS link generator for beer type resources.
     */
    @Autowired
    private BeerTypeResourceAssembler beerTypeResourceAssembler;

    /**
     * HATEOAS link generator for beer resources.
     */
    @Autowired
    private BeerResourceAssembler beerResourceAssembler;

    /**
     * Returns all countries from the database.
     *
     * @return List of countries.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<Country>> getAllCountries() {
        Iterable<Country> countries = cr.findAll();
        List<Resource<Country>> resourceList = new ArrayList<>();

        for (Country c : countries) {
            resourceList.add(resourceAssembler.toResource(c));
        }

        Resources<Resource<Country>> res = new Resources<>(resourceList,
                linkTo(CountryController.class).withSelfRel());
        MainResourceAssembler.addLinksToResources(res);

        return res;
    }

    /**
     * returns specific country from the database by given id.
     *
     * @param name Country id and name.
     * @return Chosen Country entity object.
     */
    @RequestMapping(
            value = "/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<Country> getCountryByName(@PathVariable String name) {
        Country c = cr.findOne(name);
        return this.resourceAssembler.toResource(c);
    }

    /**
     * Returns all beer types that chosen country has.
     *
     * @param countryName Country name in String format.
     * @return List of beer types.
     */
    @RequestMapping(
            value = "/{countryName}/beertypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<BeerType>> getBeerTypesByCountry(
            @PathVariable String countryName) {

        Iterable<BeerType> beerTypes
                = cr.findAllBeerTypesByCountryId(new Country(countryName));
        List<Resource<BeerType>> resources = new ArrayList<>();

        for (BeerType b : beerTypes) {
            resources.add(beerTypeResourceAssembler.toResource(b));
        }

        return new Resources<>(resources, linkTo(CountryController.class).
                slash(countryName).slash("beertypes").
                withSelfRel());
    }

    /**
     * Returns specific beer type.
     *
     * @param countryName Country name string.
     * @param beerTypeName Beer type name String.
     * @return BeerType object entity.
     */
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

    /**
     * Returns all beer by chosen beer type and country.
     *
     * @param countryName Country name string.
     * @param beerTypeName Beer type name String.
     * @return
     */
    @RequestMapping(
            value = "/{countryName}/beertypes/{beerTypeName}/beers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<Beer>> getBeersByBeerTypeAndCountry(
            @PathVariable String countryName,
            @PathVariable String beerTypeName) {

        BeerType bt = new BeerType(beerTypeName);
        Country ct = new Country(countryName);

        Iterable<Beer> beers = cr.findAllBeerByBeerTypeAndCountry(bt, ct);
        List<Resource<Beer>> beerResourceList = new ArrayList<>();

        for (Beer b : beers) {
            b.setReviews(null);
            beerResourceList.add(beerResourceAssembler.toResource(b));
        }

        return new Resources<>(beerResourceList, linkTo(BeerController.class).
                withSelfRel());
    }

    /**
     * Returns all beers by chosen country.
     *
     * @param countryName Country name string.
     * @return Beers list by country.
     */
    @RequestMapping(
            value = "/{countryName}/beers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<Beer>> getBeersByCountry(
            @PathVariable String countryName) {

        List<Beer> beers = br.findByCountry(new Country(countryName));
        List<Resource<Beer>> beerResources = new ArrayList<>();

        beers.forEach((b) -> {
            beerResources.add(beerResourceAssembler.toResource(b));
        });

        return new Resources<>(beerResources, linkTo(CountryController.class).
                slash(countryName).slash("beers").
                withSelfRel());
    }

    /**
     * Returns beer by country.
     *
     * @param countryName Country name String.
     * @param id Beer Id.
     * @return Beer entity object.
     */
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

    /**
     * Adds new Country to database.
     *
     * @param country New country entity object.
     * @return Added country.
     */
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
