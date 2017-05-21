package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions;

public class ReviewedAlreadyException extends SuperException {
    
    public ReviewedAlreadyException() {
        super(409, "Conflict", "User has already reviewed product");
    }
}
