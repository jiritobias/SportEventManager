package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SportsMenDaoImpl extends UserDaoImpl implements SportsMenDao {
    @Override
    public void create(User entity) {
        // create
        super.create(entity);
        // set role
        entity.setRole(Role.SPORTSMEN);
        entityManager.merge(entity);
    }

    @Override
    public void addToCompetition(Competition competition, User user) {
        assert competition != null;
        assert user != null;

        user.getCompetitions().add(competition);
        entityManager.merge(user);
    }
    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT s FROM User s where s.role = :role", User.class)
                .setParameter("role", Role.SPORTSMEN)
                .getResultList();
    }
}
