package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
public class BadRequestException extends SuperException {
    
    public BadRequestException(String message) {
        super(400, "Bad Request", message);
    }
}
