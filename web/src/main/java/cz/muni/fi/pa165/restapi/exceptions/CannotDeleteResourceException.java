package cz.muni.fi.pa165.restapi.exceptions;

/**
 *
 */
public class CannotDeleteResourceException extends RuntimeException {

    public CannotDeleteResourceException(String message) {
        super(message);
    }
}
