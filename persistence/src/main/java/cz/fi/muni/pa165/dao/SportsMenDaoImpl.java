package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
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
    public List<User> findAll() {
        return entityManager.createQuery("SELECT s FROM User s where s.role = :role", User.class)
                .setParameter("role", Role.SPORTSMEN)
                .getResultList();
    }
}
