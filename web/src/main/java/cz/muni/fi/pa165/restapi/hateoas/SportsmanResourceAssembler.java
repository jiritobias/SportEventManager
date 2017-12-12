package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.muni.fi.pa165.restapi.controllers.SportsManRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class SportsmanResourceAssembler extends ResourceAssemblerSupport<SportsMenDTO, SportsManResource> {

    public SportsmanResourceAssembler() {
        super(SportsManRestController.class, SportsManResource.class);
    }

    @Override
    public SportsManResource toResource(SportsMenDTO sportsMenDTO) {
        Long id = sportsMenDTO.getId();
        SportsManResource sportsManResource = new SportsManResource(sportsMenDTO);
        try {
            sportsManResource.add(linkTo(SportsManRestController.class).slash(id).withSelfRel());
        } catch (Exception ex) {

        }

        return sportsManResource;
    }
}
