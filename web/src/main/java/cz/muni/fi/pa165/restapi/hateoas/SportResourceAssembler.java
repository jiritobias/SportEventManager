package cz.muni.fi.pa165.restapi.hateoas;


import cz.fi.muni.pa165.dto.SportDTO;
import cz.muni.fi.pa165.restapi.controllers.SportRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class SportResourceAssembler extends ResourceAssemblerSupport<SportDTO, SportResource> {
    public SportResourceAssembler() {
        super(SportRestController.class, SportResource.class);
    }

    @Override
    public SportResource toResource(SportDTO sportDTO) {
        Long id = sportDTO.getId();
        SportResource sportResource = new SportResource(sportDTO);
        try {
            sportResource.add(linkTo(SportRestController.class).slash(id).withSelfRel());

        } catch (Exception ex) {

        }
        return sportResource;
    }
}
