package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;

import java.util.List;

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

}
