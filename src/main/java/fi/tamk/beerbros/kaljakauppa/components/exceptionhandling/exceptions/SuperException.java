package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions;

public abstract class SuperException extends Exception {
    
    public final int STATUS;
    public final String ERROR;
    public final String MESSAGE;
    
    public SuperException(int status, String error, String message) {
        this.STATUS = status;
        this.ERROR = error;
        this.MESSAGE = message;
    }
}
