package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions;

public class NotFoundException extends SuperException {
    
    public NotFoundException(String message) {
        super(404, "Not Found", message);
    }
}
