package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions;

/**
 * Class that represents Conflict response.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public class ReviewedAlreadyException extends SuperException {
    
    /**
     * Generates BadRequestException object.
     *
     * Status code will be 404, and Error will be "Conflict".
     */
    public ReviewedAlreadyException() {
        super(409, "Conflict", "User has already reviewed product");
    }
}
