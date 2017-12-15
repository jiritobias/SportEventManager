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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
    public final HttpEntity<Resources<UserResource>> getUsers(
            @RequestParam(value = "role", required = false, defaultValue = "SPORTSMEN") String role,
            @RequestParam(value = "limit", required = false, defaultValue = "0") long limit,
            @RequestParam(value = "birthdateBegin", required = false, defaultValue = "0000-00-00") String birthdateBegin,
            @RequestParam(value = "birthdateEnd", required = false, defaultValue = "9999-99-99") String birthdateEnd,
            @RequestParam(value = "gender", required = false, defaultValue = "ALL") String gender,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy
    ) {
        logger.debug("UserRestController getUsers()");

        List<UserResource> userResources = new ArrayList<>();

//        if (role.equalsIgnoreCase("USER")) {
//
//        } else if (role.equalsIgnoreCase("ADMIN")) {
//
//        } else if (role.equalsIgnoreCase("SPORTSMEN")) {
//            userResources = userResourceAssembler.toResources(sportsMenFacade.getAll());
//        } else if (role.equalsIgnoreCase("ALL")) {
//
//        } else {
//            throw new InvalidParameterException("Role parameters options: user, admin, sportsmen, all");
//        }

        userResources = userResourceAssembler.toResources(sportsMenFacade.getAll());

        List<UserResource> filteredResources = new ArrayList<>();

        for (UserResource resource : userResources) {
            // filter birthdate: yyyy-mm-dd
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String birthdate = simpleDateFormat.format(resource.getBirthdate());
            boolean dateInRange = birthdate.compareTo(birthdateBegin) >= 0 &&
                    birthdate.compareTo(birthdateEnd) <= 0;
            if (dateInRange) {
                // filter gender: ALL, MAN, WOMAN
                if (!gender.equalsIgnoreCase("ALL")) {
                    if (gender.equalsIgnoreCase(resource.getGender().toString())) {
                        filteredResources.add(resource);
                    }
                } else {
                    filteredResources.add(resource);
                }
            }
        }
        userResources = filteredResources;

        if (limit > 0) {
            filteredResources = new ArrayList<>();
            for (int i = 0; i < Math.min(limit, userResources.size()); i++) {
                filteredResources.add(userResources.get(i));
            }
            userResources = filteredResources;
        }

        // sort results
        switch (sortBy.toLowerCase()) {
            case "role":
                userResources.sort(Comparator.comparing(UserResource::getRole));
                break;
            case "birthdate":
                userResources.sort(Comparator.comparing(UserResource::getBirthdate));
                break;
            case "gender":
                userResources.sort(Comparator.comparing(UserResource::getGender));
                break;
            case "firstname":
                userResources.sort(Comparator.comparing(o -> o.getFirstname().toLowerCase()));
                break;
            case "lastname":
                userResources.sort(Comparator.comparing(o -> o.getLastname().toLowerCase()));
                break;
            case "name":
                userResources.sort((o1, o2) -> {
                    int c = o1.getFirstname().toLowerCase().compareTo(o2.getFirstname().toLowerCase());
                    if (c == 0) {
                        c = o1.getLastname().toLowerCase().compareTo(o2.getLastname().toLowerCase());
                    }
                    return c;
                });
            case "email":
                userResources.sort(Comparator.comparing(UserResource::getEmail));
                break;
            case "id":
                userResources.sort(Comparator.comparing(UserResource::getDtoId));
                break;
            default:
                throw new InvalidParameterException(
                        "SortBy parameter options: id, name, firstname, lastname, role, gender, birthdate, email");
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
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

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public final HttpEntity<UserResource> deleteUser(@PathVariable("id") long id) {
        SportsMenDTO sportsMenDTO = sportsMenFacade.load(id);
        if (sportsMenDTO == null) {
            throw new ResourceNotFoundException("user " + id + " not found");
        }

        UserResource resource = userResourceAssembler.toResource(sportsMenDTO);
        sportsMenFacade.delete(sportsMenDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
