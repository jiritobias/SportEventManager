package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.AuthenticateDTO;
import cz.fi.muni.pa165.dto.LoginResultDTO;

public interface LoginFacade {

    /**
     *
     */
    LoginResultDTO login(AuthenticateDTO authenticateDTO);
}
