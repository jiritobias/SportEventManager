package cz.muni.fi.pa165.restapi.hateoas;

import cz.fi.muni.pa165.dto.ChangePasswordDTO;
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
public class UserNewPasswordResourceAssembler extends ResourceAssemblerSupport<ChangePasswordDTO, UserNewPasswordResource> {

    public UserNewPasswordResourceAssembler() {
        super(UserRestController.class, UserNewPasswordResource.class);
    }

    @Override
    public UserNewPasswordResource toResource(ChangePasswordDTO changePasswordDTO) {
        UserNewPasswordResource userResource = new UserNewPasswordResource(changePasswordDTO.getId(), changePasswordDTO.getNewPassword());
        try {
            userResource.add(linkTo(UserRestController.class).slash(changePasswordDTO.getId()).withSelfRel());
        } catch (Exception ex) {

        }

        return userResource;
    }
}
