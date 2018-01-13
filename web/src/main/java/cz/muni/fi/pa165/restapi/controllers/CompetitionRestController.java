package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.AddSportsMenDTO;
import cz.fi.muni.pa165.dto.CancelRegistrationDTO;
import cz.fi.muni.pa165.dto.CompetitionDTO;
import cz.fi.muni.pa165.dto.CreateCompetitionDTO;
import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.facade.CompetitionFacade;
import cz.fi.muni.pa165.facade.SportsMenFacade;
import cz.muni.fi.pa165.restapi.ApiUris;
import cz.muni.fi.pa165.restapi.exceptions.*;
import cz.muni.fi.pa165.restapi.hateoas.CompetitionResource;
import cz.muni.fi.pa165.restapi.hateoas.CompetitionResourceAssembler;
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
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Petra Halová on 13.12.17.
 */
@RestController
@ExposesResourceFor(CompetitionDTO.class)
@RequestMapping(ApiUris.ROOT_URI_COMPETITIONS)
public class CompetitionRestController {

    final static Logger logger = LoggerFactory.getLogger(CompetitionRestController.class);

    @Autowired
    private CompetitionResourceAssembler competitionResourceAssembler;

    @Autowired
    private CompetitionFacade competitionFacade;

    @Autowired
    private SportsMenFacade sportsMenFacade;

    @RequestMapping(method = RequestMethod.GET)
    public final HttpEntity<Resources<CompetitionResource>> getCompetitions() {
        logger.debug("rest getCompetitions()");

        List<CompetitionDTO> competitionDTOList = competitionFacade.getAll();
        sportsmenCompetitionsNull(competitionDTOList);
        List<CompetitionResource> competitionResources = competitionResourceAssembler.toResources(
                competitionDTOList);

        Resources<CompetitionResource> resources = new Resources<>(competitionResources,
                linkTo(CompetitionRestController.class).withSelfRel());

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    private void sportsmenCompetitionsNull(Iterable<CompetitionDTO> competitionDTOList) {
        for (CompetitionDTO competitionDTO : competitionDTOList) {
            List<SportsMenDTO> sportsMen = competitionDTO.getSportsMen();
            for (SportsMenDTO sportsMenDTO : sportsMen) {
                sportsMenDTO.setCompetitions(null);
            }
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<CompetitionResource> createCompetition(@RequestBody @Valid CreateCompetitionDTO createCompetitionDTO, BindingResult bindingResult) throws Exception {
        logger.debug("rest createCompetition()");
        if (bindingResult.hasErrors()) {
            logger.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        Long id;
        try {
            id = competitionFacade.createCompetition(createCompetitionDTO);
        } catch (DataAccessException e) {
            throw new ResourceAlreadyExistingException("Competition already exists");
        }
        CompetitionResource resource = competitionResourceAssembler.toResource(competitionFacade.load(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<CompetitionResource> updateCompetition(@RequestBody @Valid CompetitionDTO competitionDTO, BindingResult bindingResult) throws Exception {
        logger.debug("rest updateCompetition()");
        if (bindingResult.hasErrors()) {
            logger.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        try {
            competitionFacade.update(competitionDTO);
        } catch (DataAccessException e) {
            throw new CannotDeleteResourceException("Cannot update resource");
        }

        CompetitionResource resource = competitionResourceAssembler.toResource(competitionFacade.load(competitionDTO.getId()));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public final HttpEntity<CompetitionResource> getCompetition(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getCompetition()", id);

        CompetitionDTO competitionDTO = competitionFacade.load(id);
        if (competitionDTO == null) {
            throw new ResourceNotFoundException("competition with id " + id + " not found");
        }

        sportsmenCompetitionsNull(Arrays.asList(competitionDTO));

        CompetitionResource competitionResource = competitionResourceAssembler.toResource(competitionDTO);
        return new ResponseEntity<>(competitionResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/getBySport/{sport}", method = RequestMethod.POST)
    public final HttpEntity<Resources<CompetitionResource>> listCompetitionsBySport(@PathVariable("sport") String sportName) throws Exception {
        logger.debug("rest listCompetitionsBySport()", sportName);

        List<CompetitionDTO> all = competitionFacade.getAll();
        List<CompetitionDTO> result = new ArrayList<>();

        for(CompetitionDTO comp: all){
            if (comp.getSport().getName().equals(sportName)){
                result.add(comp);
            }
        }

        sportsmenCompetitionsNull(result);

        List<CompetitionResource> competitionResources = competitionResourceAssembler.toResources(result);
        Resources<CompetitionResource> resources = new Resources<>(competitionResources,
                linkTo(CompetitionRestController.class).withSelfRel());

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public final HttpEntity<CompetitionResource> deleteCompetition(@PathVariable("id") long id) {
        CompetitionDTO competitionDTO = competitionFacade.load(id);
        //System.out.print(competitionDTO.getId());
        if (competitionDTO == null) {
            throw new ResourceNotFoundException("competition " + id + " not found");
        }

        CompetitionResource resource = competitionResourceAssembler.toResource(competitionDTO);
        competitionFacade.delete(competitionDTO);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<CompetitionResource> registerToCompetition(@RequestBody @Valid AddSportsMenDTO addSportsMenDTO, BindingResult bindingResult) throws Exception {
        logger.debug("rest registerToCompetition()");
        if (bindingResult.hasErrors()) {
            logger.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        competitionFacade.addSportsMen(addSportsMenDTO);
        CompetitionResource resource = competitionResourceAssembler.toResource(competitionFacade.load(addSportsMenDTO.getCompetition()));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/unregister", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<CompetitionResource> unregisterFromCompetition(@RequestBody @Valid CancelRegistrationDTO cancelRegistrationDTO, BindingResult bindingResult) throws Exception {
        logger.debug("rest unregisterFromCompetition()");
        if (bindingResult.hasErrors()) {
            logger.error("failed validation {}", bindingResult.toString());
            throw new InvalidRequestException("Failed validation");
        }
        sportsMenFacade.cancelRegistration(cancelRegistrationDTO);
        CompetitionResource resource = competitionResourceAssembler.toResource(competitionFacade.load(cancelRegistrationDTO.getCompetition()));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
