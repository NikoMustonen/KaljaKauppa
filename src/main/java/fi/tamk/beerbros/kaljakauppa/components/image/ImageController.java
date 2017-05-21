package fi.tamk.beerbros.kaljakauppa.components.image;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kaljakauppa/images")
public class ImageController {

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable int id) {

        try {
            InputStream in = new ClassPathResource(
                    "images/" + String.format("%06d", id) + ".jpg")
                    .getInputStream();
            return IOUtils.toByteArray(in);
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
