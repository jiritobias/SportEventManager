package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;

import java.util.List;
import java.util.Set;

/**
 * @author Petra Halová on 22.11.17.
 */
public interface CompetitionService extends BaseService<Competition> {

    /**
     * Adds given sportMan to desired competition
     * @param competition represents Competition
     * @param sportsMan represents User, which is sportsman
     */
    void addSportMen(Competition competition, User sportsMan);

    /**
     * Lists all registered sportsmen in given competition
     * @param competition given competition
     * @return list of participants in competitions
     */
    List<User> listAllRegisteredSportsMen(Competition competition);

    /**
     * Lists all competitions without participants and maybe should be deleted
     * @return competitions
     */
    Set<Competition> listAllCompetitionsWithoutParticipants();
}
