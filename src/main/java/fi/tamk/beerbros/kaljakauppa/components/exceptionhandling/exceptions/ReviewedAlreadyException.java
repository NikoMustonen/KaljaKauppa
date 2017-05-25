package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
public class ReviewedAlreadyException extends SuperException {
    
    public ReviewedAlreadyException() {
        super(409, "Conflict", "User has already reviewed product");
    }
}
