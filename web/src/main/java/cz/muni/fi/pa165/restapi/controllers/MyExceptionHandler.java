package cz.muni.fi.pa165.restapi.controllers;

import cz.muni.fi.pa165.restapi.exceptions.CannotDeleteResourceException;
import cz.muni.fi.pa165.restapi.exceptions.ErrorResource;
import cz.muni.fi.pa165.restapi.exceptions.ResourceAlreadyExistingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@ControllerAdvice
public class MyExceptionHandler {

    final static Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    @ResponseBody
    protected ResponseEntity<ErrorResource> handleProblem(Exception e) {


        ErrorResource error = new ErrorResource(e.getClass().getSimpleName(), e.getMessage());

        HttpStatus httpStatus;
        if (e instanceof ResourceAlreadyExistingException) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (e instanceof CannotDeleteResourceException) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        log.debug("handleProblem({}(\"{}\")) httpStatus={}", e.getClass().getName(), e.getMessage(), httpStatus);
        return new ResponseEntity<>(error, httpStatus);
    }


}
