package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.fi.muni.pa165.facade.SportsMenFacade;
import cz.muni.fi.pa165.restapi.ApiUris;
import cz.muni.fi.pa165.restapi.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.restapi.hateoas.SportsManResource;
import cz.muni.fi.pa165.restapi.hateoas.SportsmanResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Sportsmen REST Controller
 *
 * @author Martin Smid
 */
@RestController
@ExposesResourceFor(SportsMenDTO.class)
@RequestMapping(ApiUris.ROOT_URI_SPORTSMEN)
public class SportsManRestController {

    final static Logger logger = LoggerFactory.getLogger(SportsManRestController.class);

    @Autowired
    private SportsmanResourceAssembler sportsmanResourceAssembler;

    @Autowired
    private SportsMenFacade sportsMenFacade;

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<SportsManResource>> getSportsMen() {
        logger.debug("SportRestController getSportsMen()");

        List<SportsManResource> sportsManResources = sportsmanResourceAssembler.toResources(sportsMenFacade.getAll());

        Resources<SportsManResource> resources = new Resources<>(
                sportsManResources,
                linkTo(SportsManRestController.class).withSelfRel(),
                linkTo(SportsManRestController.class).slash("/create").withRel("create")
        );

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<SportsManResource> getSportsman(@PathVariable("id") long id) throws Exception {
        logger.debug("SportsManRestController getSprtsman({})", id);

        SportsMenDTO sportsMenDTO = sportsMenFacade.load(id);
        if (sportsMenDTO == null) {
            throw new ResourceNotFoundException("sportsman " + id + " not found");
        }

        SportsManResource sportsManResource = sportsmanResourceAssembler.toResource(sportsMenDTO);
        return new ResponseEntity<SportsManResource>(sportsManResource, HttpStatus.OK);
    }
}
