package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
public class NotFoundException extends SuperException {
    
    public NotFoundException(String message) {
        super(404, "Not Found", message);
    }
}
