package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.CompetitionDTO;
import cz.muni.fi.pa165.restapi.controllers.CompetitionRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Petra Halova on 14.12.17.
 */
@Component
public class CompetitionResourceAssembler extends ResourceAssemblerSupport<CompetitionDTO, CompetitionResource> {

    public CompetitionResourceAssembler() {
        super(CompetitionRestController.class, CompetitionResource.class);
    }

    @Override
    public CompetitionResource toResource(CompetitionDTO competitionDTO) {
        Long id = competitionDTO.getId();
        CompetitionResource competitionResource = new CompetitionResource(competitionDTO);
        try {
            competitionResource.add(linkTo(CompetitionRestController.class).slash(id).withSelfRel());

        } catch (Exception ex) {
            Logger.getLogger(CompetitionResourceAssembler.class.getName()).log(Level.SEVERE, "could not link resource from CompetitionControllerHateoas", ex);
        }
        return competitionResource;
    }
}

