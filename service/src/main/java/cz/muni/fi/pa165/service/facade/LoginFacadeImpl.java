package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.AuthenticateDTO;
import cz.fi.muni.pa165.dto.LoginResultDTO;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.facade.LoginFacade;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jiritobias
 */
@Service
@Transactional
public class LoginFacadeImpl implements LoginFacade {
    @Qualifier("userServiceImpl")
    @Autowired
    UserService userService;

    @Override
    public LoginResultDTO login(AuthenticateDTO authenticateDTO) {
        User user = userService.findByEmail(authenticateDTO.getEmail());
        boolean authenticate = userService.authenticate(user, authenticateDTO.getPassword());
        return new LoginResultDTO(user.getRole(), authenticate);
    }
}
