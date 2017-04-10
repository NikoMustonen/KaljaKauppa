package fi.tamk.beerbros.kaljakauppa.rest;

import fi.tamk.beerbros.kaljakauppa.entities.Beer;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Beers {

    @RequestMapping("/beers")
    public List<Beer> getBeers() {
        return Arrays.asList(
                new Beer("1", "Karhu", "Hyvaa", 0.99f),
                new Beer("2", "Koff", "Hyvaa", 0.99f),
                new Beer("3", "Lapinkulta", "Hyvaa", 0.99f),
                new Beer("4", "Carlsberg", "Hyvaa", 0.99f),
                new Beer("5", "Heineken", "Hyvaa", 0.99f),
                new Beer("6", "Beer Lao", "Hyvaa", 0.99f),
                new Beer("7", "Magumbu", "Hyvaa", 0.99f)
        );
    }
}
