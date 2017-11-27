package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.CancelRegistrationDTO;
import cz.fi.muni.pa165.dto.ChangePasswordDTO;
import cz.fi.muni.pa165.dto.CreateSportsMenDTO;
import cz.fi.muni.pa165.dto.RegistToCompetitionDTO;
import cz.fi.muni.pa165.dto.ResetPasswordDTO;
import cz.fi.muni.pa165.dto.SportsMenDTO;

import java.util.Date;
import java.util.List;

/**
 * @author jiritobias
 */
public interface SportsMenFacade extends BaseFacade<SportsMenDTO> {

    /**
     * Create new SportsMen.
     */
    Long createSportsMen(CreateSportsMenDTO createSportsMenDTO);

    /**
     * Findes sportsMen by birth day.
     */
    List<SportsMenDTO> findByBirthDay(Date birthDay);

    /**
     * Register sportsMen to competition.
     */
    void registerToCompetition(RegistToCompetitionDTO registToCompetitionDTO);

    /**
     * Cancel registration.
     */
    void cancelRegistration(CancelRegistrationDTO cancelRegistrationDTO);

    /**
     * Reset user password.
     */
    void resetPassword(ResetPasswordDTO resetPasswordDTO);

    /**
     * Change user password.
     */
    void changePassword(ChangePasswordDTO changePasswordDTO);
}
