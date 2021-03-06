package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.enums.Role;

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
     * @return new password
     */
    String resetPassword(ResetPasswordDTO resetPasswordDTO);

    /**
     * Change user password.
     */
    void changePassword(ChangePasswordDTO changePasswordDTO);

    /**
     * Gets all users by role.
     *
     * @param role role
     * @return collection of users with role
     */
    List<SportsMenDTO> getAll(Role role);

    /**
     * Updates user.
     */
    void update(SportsMenDTO sportsMenDTO);

    /**
     * Load sportsman with given email
     * @param email
     * @return sportsman
     */
    SportsMenDTO loadByEmail(FindByEmailDTO email);
}
