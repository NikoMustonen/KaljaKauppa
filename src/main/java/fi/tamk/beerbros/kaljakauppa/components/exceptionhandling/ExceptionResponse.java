package fi.tamk.beerbros.kaljakauppa.components.exceptionhandling;

/**
 * Entity class for exception responses.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public class ExceptionResponse {

    /**
     * Response status.
     */
    private Integer status;
    
    /**
     * Error message.
     */
    private String error;
    
    /**
     * Exception message.
     */
    private String message;

    /**
     * Creates empty ExceptionResponce object.
     */
    public ExceptionResponse() {
    }

    /**
     * Generates Exception response object with given values.
     * 
     * @param statusCode Exception status code.
     * @param error Exception error.
     * @param message Exception message.
     */
    public ExceptionResponse(Integer statusCode, String error, String message) {
        this.status = statusCode;
        this.error = error;
        this.message = message;
    }

    /**
     * Returns exception status.
     * 
     * @return Integer status value.
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets status value for response.
     * 
     * @param statusCode New status value.
     */
    public void setStatus(Integer statusCode) {
        this.status = statusCode;
    }

    /**
     * Returns message.
     * 
     * @return Message string.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message for the response.
     * 
     * @param message Message string.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns response error.
     * 
     * @return Error string.
     */
    public String getError() {
        return error;
    }

    /**
     * Sets error for the response.
     * 
     * @param error Error string.
     */
    public void setError(String error) {
        this.error = error;
    }
}
