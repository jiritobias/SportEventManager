package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.muni.fi.pa165.restapi.controllers.UserRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Assembler for User resources.
 *
 * @author Martin Smid
 */
@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<SportsMenDTO, UserResource> {

    public UserResourceAssembler() {
        super(UserRestController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(SportsMenDTO sportsMenDTO) {
        Long id = sportsMenDTO.getId();
        UserResource userResource = new UserResource(sportsMenDTO);
        try {
            userResource.add(linkTo(UserRestController.class).slash(id).withSelfRel());
        } catch (Exception ex) {

        }

        return userResource;
    }
}
