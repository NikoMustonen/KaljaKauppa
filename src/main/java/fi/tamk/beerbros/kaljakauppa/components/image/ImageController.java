package fi.tamk.beerbros.kaljakauppa.components.image;

import fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions.NotFoundException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
@RestController
@RequestMapping("/kaljakauppa/images")
public class ImageController {

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable int id) throws NotFoundException {

        try {
            //System.out.println("images/" + String.format("%06d", id) + ".jpg");
            InputStream in = new ClassPathResource(
                    "images/" + String.format("%06d", id) + ".jpg")
                    .getInputStream();
            return IOUtils.toByteArray(in);
        } catch(IOException e) {
            throw new NotFoundException("");
        }
    }

    @RequestMapping(
            //value = "/{id}",
            method = RequestMethod.POST,
            consumes = MediaType.IMAGE_JPEG_VALUE,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] addImage(@RequestBody byte[] image) {

        return image;
        /*try {
            InputStream in = new ByteArrayInputStream(b);
            BufferedImage bi = ImageIO.read(in);
            
            System.out.println(bi == null);
            /*ImageIO.write(bImageFromConvert, "jpg", new File(
                        "C:\\Users\\Nikon-PC\\Pictures\\image.jpg"));
        } catch(IOException e) {
            System.out.println("Error");
        }
        /*try {
            System.out.println("images/" + String.format("%06d", id) + ".jpg");
            InputStream in = new ClassPathResource(
                    "images/" + String.format("%06d", id) + ".jpg")
                    .getInputStream();
            return IOUtils.toByteArray(in);
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }*/
    }
}
