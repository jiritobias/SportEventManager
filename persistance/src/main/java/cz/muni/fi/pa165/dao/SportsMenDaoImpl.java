package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Competition;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.Role;

public class SportsMenDaoImpl extends UserDaoImpl implements SportsMenDao {
    @Override
    public void create(User entity) {
        // set role
        entity.setRole(Role.SPORTSMEN);
        // create
        super.create(entity);
    }

    @Override
    public void addToCompetition(Competition competition, User user) {
        assert competition != null;
        assert user != null;

        user.getCompetitions().add(competition);
        entityManager.merge(user);
    }
}
