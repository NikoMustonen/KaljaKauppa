package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions;

/**
 * Base class for application specific exceptions.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
public abstract class SuperException extends Exception {

    /**
     * Status code in Integer format.
     */
    public final int STATUS;

    /**
     * Error text String.
     */
    public final String ERROR;

    /**
     * Exception message String.
     */
    public final String MESSAGE;

    /**
     * Constructs SuperException object
     *
     * @param status Status code Integer.
     * @param error Error String.
     * @param message Exception message.
     */
    public SuperException(int status, String error, String message) {
        this.STATUS = status;
        this.ERROR = error;
        this.MESSAGE = message;
    }
}
