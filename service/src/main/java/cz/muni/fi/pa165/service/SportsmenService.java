package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Martin Smid
 */
@Service
public interface SportsmenService extends UserService {

    /**
     * Registers the user in the competition.
     *
     * @param user        user to register to the competition
     * @param competition competition
     */
    void registerToCompetition(User user, Competition competition);

    /**
     * Unregister the user from the competition.
     *
     * @param user        user to unregister
     * @param competition competition
     */
    void unregisterFromCompetition(User user, Competition competition);

    /**
     * Finds all competitions where the user is registered.
     *
     * @param user user
     * @return collection of competitions with the user
     */
    List<Competition> findAllRegisteredCompetitions(User user);
}
