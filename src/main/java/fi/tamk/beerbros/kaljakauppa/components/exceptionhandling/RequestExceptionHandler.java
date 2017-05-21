package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling;

import fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions.SuperException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RequestExceptionHandler {
    
    @ExceptionHandler(SuperException.class)
    public ResponseEntity<ExceptionResponse> handle(SuperException e) {
        ExceptionResponse er = 
                new ExceptionResponse(e.STATUS, e.ERROR, e.MESSAGE);
        ResponseEntity<ExceptionResponse> re =
                ResponseEntity.status(e.STATUS).body(er);
        return re;
    }
}
