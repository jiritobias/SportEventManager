package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.facade.SportFacade;
import cz.muni.fi.pa165.restapi.hateos.SportResource;
import cz.muni.fi.pa165.restapi.hateos.SportResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author jiritobias
 */
@RestController
@ExposesResourceFor(SportDTO.class)
@RequestMapping("/sports")
public class SportRestController {

    @Autowired
    private SportResourceAssembler sportResourceAssembler;
    @Autowired
    private SportFacade sportFacade;

    final static Logger log = LoggerFactory.getLogger(SportRestController.class);

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<SportResource>> getSports(){
        // TODO
        List<SportResource> sportResources = sportResourceAssembler.toResources(sportFacade.getAll());

        Resources<SportResource> resources = new Resources<>(sportResources,
                linkTo(SportRestController.class).withSelfRel(),
                linkTo(SportRestController.class).slash("/create").withRel("create"));

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
