package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions;

/**
 * Class that represents NotFound response.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public class NotFoundException extends SuperException {
    
    /**
     * Generates BadRequestException object.
     *
     * Status code will be 404, and Error will be "Not Found".
     *
     * @param message Exception message.
     */
    public NotFoundException(String message) {
        super(404, "Not Found", message);
    }
}
