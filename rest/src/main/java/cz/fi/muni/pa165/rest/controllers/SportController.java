package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.facade.SportFacade;
import cz.fi.muni.pa165.rest.ApiUris;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(ApiUris.ROOT_URI_SPORTS)
public class SportController {
    final static Logger logger = LoggerFactory.getLogger(SportController.class);

    @Autowired
    private SportFacade sportFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<SportDTO> getSports() {
        logger.debug("rest getSports()");

        return sportFacade.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final SportDTO getSport(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getSport({})", id);

        SportDTO sport = sportFacade.load(id);
        if (sport == null) {
            throw new ResourceNotFoundException();
        }

        return sport;
    }
}
