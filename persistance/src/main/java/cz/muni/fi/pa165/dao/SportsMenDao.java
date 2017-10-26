package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Competition;
import cz.muni.fi.pa165.entity.User;

public interface SportsMenDao extends UserDao {

    void addToCompetition(Competition competition, User user);
}
