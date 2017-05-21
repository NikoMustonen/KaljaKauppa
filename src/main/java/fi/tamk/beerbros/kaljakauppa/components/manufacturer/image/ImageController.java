package fi.tamk.beerbros.kaljakauppa.components.manufacturer.image;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.io.IOUtils;

public class ImageController {

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable int id) {

        try {
            InputStream in = this.getClass().getResourceAsStream("/images/no_image.jpg");
            return IOUtils.toByteArray(in);
        } catch(IOException e) {
            return null;
        }
    }
}
