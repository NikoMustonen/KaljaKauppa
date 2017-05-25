package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions;

/**
 * Class that represents BadRequest.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public class BadRequestException extends SuperException {

    /**
     * Generates BadRequestException object.
     *
     * Status code will be 400, and Error will be "Bad Request".
     *
     * @param message Exception message.
     */
    public BadRequestException(String message) {
        super(400, "Bad Request", message);
    }
}
