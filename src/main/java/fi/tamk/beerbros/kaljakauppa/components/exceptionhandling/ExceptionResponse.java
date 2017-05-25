package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
public class ExceptionResponse {

    private Integer status;
    private String error;
    private String message;
    
    public ExceptionResponse(){}
    public ExceptionResponse(Integer statusCode, String error, String message){
        this.status = statusCode;
        this.error = error;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer statusCode) {
        this.status = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
