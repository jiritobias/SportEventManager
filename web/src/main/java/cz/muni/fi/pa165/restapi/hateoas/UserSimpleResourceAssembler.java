package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.muni.fi.pa165.restapi.controllers.UserRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class UserSimpleResourceAssembler extends ResourceAssemblerSupport<SportsMenDTO, UserSimpleResource> {

    public UserSimpleResourceAssembler() {
        super(UserRestController.class, UserSimpleResource.class);
    }

    @Override
    public UserSimpleResource toResource(SportsMenDTO sportsMenDTO) {
        Long id = sportsMenDTO.getId();
        UserSimpleResource userResource = new UserSimpleResource(sportsMenDTO);
        try {
            userResource.add(linkTo(UserRestController.class).slash(id).withSelfRel());
        } catch (Exception ex) {

        }
        return userResource;
    }
}
