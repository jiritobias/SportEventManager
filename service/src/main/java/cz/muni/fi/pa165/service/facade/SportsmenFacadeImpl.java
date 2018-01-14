package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.facade.SportsMenFacade;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CompetitionService;
import cz.muni.fi.pa165.service.SportsmenService;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author Martin Smid
 */
@Service
@Transactional
public class SportsmenFacadeImpl implements SportsMenFacade {

    @Autowired
    private SportsmenService sportsmenService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void update(SportsMenDTO sportsMenDTO) {
        sportsmenService.update(beanMappingService.mapTo(sportsMenDTO, User.class));
    }

    @Override
    public Long createSportsMen(CreateSportsMenDTO createSportsMenDTO) {
        User sportsman = beanMappingService.mapTo(createSportsMenDTO, User.class);
        userService.registerUser(sportsman, createSportsMenDTO.getPassword(), createSportsMenDTO.getEmail(), createSportsMenDTO.getRole());
        return sportsman.getId();
    }

    @Override
    public List<SportsMenDTO> findByBirthDay(Date birthDay) {
        List<User> found = sportsmenService.findByBirthdate(birthDay);
        return beanMappingService.mapTo(found, SportsMenDTO.class);
    }

    @Override
    public void registerToCompetition(RegistToCompetitionDTO registToCompetitionDTO) {
        User sportsman = sportsmenService.findById(registToCompetitionDTO.getSportsMen());
        Competition competition = competitionService.findById(registToCompetitionDTO.getCompetition());
        sportsmenService.registerToCompetition(sportsman, competition);
    }

    @Override
    public void cancelRegistration(CancelRegistrationDTO cancelRegistrationDTO) {
        User sportsman = sportsmenService.findById(cancelRegistrationDTO.getSportsMen());
        Competition competition = competitionService.findById(cancelRegistrationDTO.getCompetition());
        sportsmenService.unregisterFromCompetition(sportsman, competition);
    }

    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = sportsmenService.findById(resetPasswordDTO.getId());
        return sportsmenService.resetPassword(user, resetPasswordDTO.getEmail());
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = sportsmenService.findById(changePasswordDTO.getId());
        sportsmenService.changePassword(user, changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
    }

    @Override
    public List<SportsMenDTO> getAll(Role role) {
        return beanMappingService.mapTo(userService.findAll(role), SportsMenDTO.class);
    }

    @Override
    public void delete(SportsMenDTO dto) {
        User sportsman = sportsmenService.findById(dto.getId());
        sportsmenService.delete(sportsman);
    }

    @Override
    public SportsMenDTO load(Long id) {
        User sportsman = sportsmenService.findById(id);
        return beanMappingService.mapTo(sportsman, SportsMenDTO.class);
    }

    @Override
    public List<SportsMenDTO> getAll() {
        return beanMappingService.mapTo(userService.findAll(), SportsMenDTO.class);
    }

    @Override
    public SportsMenDTO loadByEmail(String email){
        return beanMappingService.mapTo(userService.findByEmail(email), SportsMenDTO.class);
    }
}
