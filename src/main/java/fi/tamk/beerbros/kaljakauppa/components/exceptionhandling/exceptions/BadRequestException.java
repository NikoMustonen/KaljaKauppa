package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions;

public class BadRequestException extends SuperException {
    
    public BadRequestException(String message) {
        super(400, "Bad Request", message);
    }
}
