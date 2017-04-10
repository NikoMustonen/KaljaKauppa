package fi.tamk.beerbros.kaljakauppa.components.manufacturer;

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
@RequestMapping("/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerRepository mr;
    
    @Autowired
    private ManufacturerResourceAssembler resourceAssembler;
    
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
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<Manufacturer> addManufacturer(@RequestBody Manufacturer manufacturer) {
        Manufacturer m = mr.save(manufacturer);
        return this.resourceAssembler.toResource(m);
    }
}
