package cz.muni.fi.pa165.restapi.exceptions;

public class ResourceAlreadyExistingException extends RuntimeException {

    public ResourceAlreadyExistingException(String message) {
        super(message);
    }
}
