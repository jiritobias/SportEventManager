package cz.muni.fi.pa165.restapi.exceptions;

/**
 *
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
