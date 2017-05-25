package fi.tamk.beerbros.kaljakauppa.components.image;

import fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions.*;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for Image resources.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@RestController
@RequestMapping("/kaljakauppa/images")
public class ImageController {

    /**
     * Fetch image from the backend.
     *
     * @param id Image id.
     * @return Jpeg image byte array.
     * @throws NotFoundException Is thrown if image is not found.
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable int id) throws NotFoundException {

        try {
            InputStream in = new ClassPathResource(
                    "images/" + String.format("%06d", id) + ".jpg")
                    .getInputStream();
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new NotFoundException("");
        }
    }

    /**
     * Adds image to backend server.
     *
     * Under construction.
     *
     * @param image Image in byte array format.
     * @return Image.
     */
    @RequestMapping(
            //value = "/{id}",
            method = RequestMethod.POST,
            consumes = MediaType.IMAGE_JPEG_VALUE,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] addImage(@RequestBody byte[] image) {

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
        return null;
    }
}
