package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Team;
import cz.fi.muni.pa165.entity.User;

public interface SportsMenDao extends UserDao {
    /**
     * Add sportsMan to competiton.
     */
    void addToCompetition(Competition competition, User sportsMan);

    /**
     *Removes sportsmen from competition.
     */
    void removeFromCompetition(Competition competition, User sportsMan);

    /**
     * Add sportsMan to team.
     */
    void addToTeam(Team team, User sportsMan);

    /**
     * Remove sportsMen from team.
     */
    void removeFromTeam(Team team, User sportsMan);
}
