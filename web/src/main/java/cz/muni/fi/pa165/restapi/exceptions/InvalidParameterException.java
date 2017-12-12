package cz.muni.fi.pa165.restapi.exceptions;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String message) {
        super(message);
    }
}
