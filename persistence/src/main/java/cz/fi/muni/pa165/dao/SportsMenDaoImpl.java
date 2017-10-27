package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Team;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author jiritobias
 */
@Repository
public class SportsMenDaoImpl extends UserDaoImpl implements SportsMenDao {
    @Override
    public void create(User sportsMan) {
        // create
        super.create(sportsMan);
        // set role
        sportsMan.setRole(Role.SPORTSMEN);
        entityManager.merge(sportsMan);
    }

    @Override
    public void addToCompetition(Competition competition, User sportsMan) {
        assert competition != null;
        assert sportsMan != null;

        sportsMan.getCompetitions().add(competition);
        entityManager.merge(sportsMan);
    }

    @Override
    public void removeFromCompetition(Competition competition, User sportsMan) {
        assert competition != null;
        assert sportsMan != null;

        sportsMan.removeFromCompetition(competition);
        entityManager.merge(sportsMan);
    }

    @Override
    public void addToTeam(Team team, User sportsMan) {
        throw new UnsupportedOperationException("Not implemented yet!"); // TODO
    }

    @Override
    public void removeFromTeam(Team team, User sportsMan) {
        throw new UnsupportedOperationException("Not implemented yet!"); // TODO
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT s FROM User s where s.role = :role", User.class)
                .setParameter("role", Role.SPORTSMEN)
                .getResultList();
    }
}
