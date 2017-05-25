package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling;

import fi.tamk.beerbros.kaljakauppa.components.exceptionhandling.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Class for cathing application specific exceptions..
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@ControllerAdvice
public class RequestExceptionHandler {

    /**
     * Handles every Exception under SuperException
     *
     * @param e SuperException object.
     * @return Handled response.
     */
    @ExceptionHandler(SuperException.class)
    public ResponseEntity<ExceptionResponse> handle(SuperException e) {
        ExceptionResponse er
                = new ExceptionResponse(e.STATUS, e.ERROR, e.MESSAGE);
        ResponseEntity<ExceptionResponse> re
                = ResponseEntity.status(e.STATUS).body(er);
        return re;
    }
}
