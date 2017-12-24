package cz.muni.fi.pa165.restapi.exceptions;

public class LoginFailedException extends RuntimeException  {
    public LoginFailedException(String message) {
        super(message);
    }
}
