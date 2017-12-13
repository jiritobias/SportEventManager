package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.facade.SportsMenFacade;
import cz.muni.fi.pa165.restapi.ApiUris;
import cz.muni.fi.pa165.restapi.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.UserResource;
import cz.muni.fi.pa165.restapi.hateoas.UserResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private SportsMenFacade sportsMenFacade;

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<UserResource>> getSportsMen(@RequestParam(value = "role", required = false, defaultValue = "SPORTSMEN") String role) {
        logger.debug("SportRestController getSportsMen()");

        List<UserResource> userResources = new ArrayList<>();

        if (role.equalsIgnoreCase("USER")) {

        } else if (role.equalsIgnoreCase("ADMIN")) {

        } else if (role.equalsIgnoreCase("SPORTSMEN")) {
            userResources = userResourceAssembler.toResources(sportsMenFacade.getAll());
        } else if (role.equalsIgnoreCase("ALL")) {

        } else {
            throw new InvalidParameterException("Role parameters options: user, admin, sportsmen, all");
        }

        Resources<UserResource> resources = new Resources<>(
                userResources,
                linkTo(UserRestController.class).withSelfRel(),
                linkTo(UserRestController.class).slash("/create").withRel("create")
        );

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<UserResource> getSportsman(@PathVariable("id") long id) throws Exception {
        logger.debug("SportsManRestController getSprtsman({})", id);

        SportsMenDTO sportsMenDTO = sportsMenFacade.load(id);
        if (sportsMenDTO == null) {
            throw new ResourceNotFoundException("sportsman " + id + " not found");
        }

        UserResource userResource = userResourceAssembler.toResource(sportsMenDTO);
        return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
    }
}
