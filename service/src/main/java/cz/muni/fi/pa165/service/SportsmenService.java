package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Martin Smid
 */
@Service
public interface SportsmenService extends UserService {

    /**
     * Registers the sportsman in the competition.
     *
     * @param sportsman   sportsman to register to the competition
     * @param competition competition
     * @throws DataAccessException in a case of failure on DAO layer
     */
    void registerToCompetition(User sportsman, Competition competition) throws DataAccessException;

    /**
     * Unregister the sportsman from the competition.
     *
     * @param sportsman   sportsman to unregister
     * @param competition competition
     * @throws DataAccessException in a case of failure on DAO layer
     */
    void unregisterFromCompetition(User sportsman, Competition competition) throws DataAccessException;

    /**
     * Finds all competitions where the sportsman is registered.
     *
     * @param sportsman sportsman
     * @return collection of competitions with the sportsman
     * @throws DataAccessException in a case of failure on DAO layer
     */
    List<Competition> findAllRegisteredCompetitions(User sportsman) throws DataAccessException;
}
