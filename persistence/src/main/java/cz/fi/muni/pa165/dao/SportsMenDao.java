package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;

/**
 * @author jiritobias
 */
public interface SportsMenDao extends UserDao {
    /**
     * Add sportsMan to competiton.
     */
    void addToCompetition(Competition competition, User sportsMan);

    /**
     * Removes sportsmen from competition.
     */
    void removeFromCompetition(Competition competition, User sportsMan);

}
