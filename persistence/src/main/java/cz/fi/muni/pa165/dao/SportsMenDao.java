package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;

public interface SportsMenDao extends UserDao {

    void addToCompetition(Competition competition, User user);
}
