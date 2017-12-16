package cz.muni.fi.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.AuthenticateDTO;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.facade.SportsMenFacade;
import cz.muni.fi.pa165.restapi.ApiUris;
import cz.muni.fi.pa165.restapi.exceptions.LoginFailedException;
import cz.muni.fi.pa165.restapi.hateoas.LoginResource;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping(ApiUris.ROOT_URI_AUTH)
public class AuthController {

    @Autowired
    SportsMenFacade sportsMenFacade;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public final HttpEntity<LoginResource> login(@RequestBody @Valid AuthenticateDTO authenticateDTO, BindingResult bindingResult) {
        String email = authenticateDTO.getEmail();
        String password = authenticateDTO.getPassword();

        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            throw new LoginFailedException("Empty input!");
        }

        boolean authenticate = sportsMenFacade.authenticate(authenticateDTO);
        if(!authenticate){
            throw new LoginFailedException("Login failed!");
        }

        LoginResource loginResource = new LoginResource();
        loginResource.setPsswd(password);
        loginResource.setUsername(email);
        loginResource.setRole(Role.ADMINISTRATOR); // TODO get role

        return new ResponseEntity<>(loginResource, HttpStatus.OK);
    }

}
