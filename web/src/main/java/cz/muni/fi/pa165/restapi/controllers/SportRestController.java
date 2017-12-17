package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.facade.SportFacade;
import cz.muni.fi.pa165.restapi.ApiUris;
import cz.muni.fi.pa165.restapi.exceptions.CannotDeleteResourceException;
import cz.muni.fi.pa165.restapi.exceptions.InvalidRequestException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.SportResource;
import cz.muni.fi.pa165.restapi.hateoas.SportResourceAssembler;
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
import java.util.List;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author jiritobias
 */
@RestController
@ExposesResourceFor(SportDTO.class)
@RequestMapping(ApiUris.ROOT_URI_SPORTS)
public class SportRestController {

    final static Logger logger = LoggerFactory.getLogger(SportRestController.class);

    @Autowired
    private SportResourceAssembler sportResourceAssembler;

    @Autowired
    private SportFacade sportFacade;


    final static Logger log = LoggerFactory.getLogger(SportRestController.class);

    /**
     * Get lists of sports.
     * curl -i -X GET http://localhost:8080/pa165/rest/sports
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<SportResource>> getSports() {
        log.debug("resController getSports()");

        List<SportResource> sportResources = sportResourceAssembler.toResources(sportFacade.getAll());

        Resources<SportResource> resources = new Resources<>(sportResources,
                linkTo(SportRestController.class).withSelfRel(),
                linkTo(SportRestController.class).slash("/create").withRel("create"));

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<SportResource> createSport(@RequestBody @Valid SportDTO sportDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest createSport()");
        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id;
        try {
            id = sportFacade.create(sportDTO);
        } catch (DataAccessException e) {
            throw new ResourceAlreadyExistingException("Sport already exists");
        }
        SportResource resource = sportResourceAssembler.toResource(sportFacade.load(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<SportResource> updateSport(@RequestBody @Valid SportDTO sportDTO, BindingResult bindingResult) throws Exception {
        log.debug("rest updateSport()");
        if (bindingResult.hasErrors()) {
            log.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id;
        try {
            SportDTO load = sportFacade.load(sportDTO.getId());
            sportFacade.delete(load);
            id = sportFacade.create(new SportDTO(null, sportDTO.getName())); // TODO Update
        } catch (DataAccessException e) {
            throw new CannotDeleteResourceException("Cannot update or delete resource");
        }

        SportResource resource = sportResourceAssembler.toResource(sportFacade.load(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Get a sport with ID.
     * $ curl -i -X POST http://localhost:8080/pa165/rest/sports/1
     *
     * @param id ID of sport
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public final HttpEntity<SportResource> getSport(@PathVariable("id") long id) throws Exception {
        logger.debug("restController getSport({})", id);

        SportDTO sportDTO = sportFacade.load(id);
        if (sportDTO == null) {
            throw new ResourceNotFoundException("sport " + id + " not found");
        }

        SportResource sportResource = sportResourceAssembler.toResource(sportDTO);
        return new ResponseEntity<>(sportResource, HttpStatus.OK);
    }

}
