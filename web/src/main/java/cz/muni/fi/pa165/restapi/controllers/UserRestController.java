package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.CreateSportsMenDTO;
import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.fi.muni.pa165.facade.SportsMenFacade;
import cz.muni.fi.pa165.restapi.ApiUris;
import cz.muni.fi.pa165.restapi.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.UserResource;
import cz.muni.fi.pa165.restapi.hateoas.UserResourceAssembler;
import cz.muni.fi.pa165.restapi.hateoas.UserSimpleResource;
import cz.muni.fi.pa165.restapi.hateoas.UserSimpleResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Sportsmen REST Controller
 *
 * @author Martin Smid
 */
@RestController
@ExposesResourceFor(SportsMenDTO.class)
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UserRestController {

    final static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserResourceAssembler userResourceAssembler;
    @Autowired
    private UserSimpleResourceAssembler userSimpleResourceAssembler;

    @Autowired
    private SportsMenFacade sportsMenFacade;

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<UserResource>> getUsers(
            @RequestParam(value = "role", required = false, defaultValue = "SPORTSMEN") String role,
            @RequestParam(value = "limit", required = false, defaultValue = "0") long limit
            ) {
        logger.debug("UserRestController getUsers()");

        List<UserResource> userResources = new ArrayList<>();

        if (role.equalsIgnoreCase("USER")) {

        } else if (role.equalsIgnoreCase("ADMIN")) {

        } else if (role.equalsIgnoreCase("SPORTSMEN")) {
            userResources = userResourceAssembler.toResources(sportsMenFacade.getAll());
        } else if (role.equalsIgnoreCase("ALL")) {

        } else {
            throw new InvalidParameterException("Role parameters options: user, admin, sportsmen, all");
        }

        if (limit > 0) {
            List<UserResource> newResource = new ArrayList<>();
            for (int i = 0; i < limit; i++) {
                newResource.add(userResources.get(i));
            }
            userResources = newResource;
        }

        Resources<UserResource> resources = new Resources<>(
                userResources,
                linkTo(UserRestController.class).withSelfRel(),
                linkTo(UserRestController.class).slash("/create").withRel("create")
        );

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<UserResource> getUser(@PathVariable("id") long id) throws Exception {
        logger.debug("UserManRestController getUser({})", id);

        SportsMenDTO sportsMenDTO = sportsMenFacade.load(id);
        if (sportsMenDTO == null) {
            throw new ResourceNotFoundException("user " + id + " not found");
        }

        UserResource userResource = userResourceAssembler.toResource(sportsMenDTO);
        return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<UserResource> createUser(@RequestBody @Valid CreateSportsMenDTO sportsMenDTO, BindingResult bindingResult) {
        logger.debug("UserRestController createUser()");

        if (bindingResult.hasErrors()) {
            logger.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }

        Long id;
        try {
            id = sportsMenFacade.createSportsMen(sportsMenDTO);
        } catch (DataAccessException e) {
            throw new ResourceAlreadyExistingException("User already exists");
        }
        UserResource resource = userResourceAssembler.toResource(sportsMenFacade.load(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
