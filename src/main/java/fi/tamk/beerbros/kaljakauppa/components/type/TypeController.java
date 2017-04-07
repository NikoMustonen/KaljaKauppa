package fi.tamk.beerbros.kaljakauppa.components.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypeController {

    @Autowired
    TypeRepository tr;

    @RequestMapping(value = "/maintypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Type> getMainTypes() {
        return tr.findMainTypes();
    }

    @RequestMapping(value = "/maintypes/{mainTypeName}/subtypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Type> getSubTypesByMainType(@PathVariable("mainTypeName") String mainTypeName) {
        Type mainType = new Type(mainTypeName, null);
        return tr.findSubTypesByMainType(mainType);
    }

    @RequestMapping(value = "/types", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public void addType(@RequestBody Type type) {
        tr.save(type);
    }
}
