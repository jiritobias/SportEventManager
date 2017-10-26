package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public User findById(Long id) {
        return findByIdAndClass(User.class, id);
    }

    @Override
    public void create(User entity) {
        entity.setRole(Role.USER);

        super.create(entity);
    }

    @Override
    public List<User> findByGendre(Gendre gendre) {
        assert gendre != null;

        return entityManager
                .createQuery("SELECT s FROM User s where s.gendre = :gend", User.class)
                .setParameter("gend", gendre)
                .getResultList();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT s FROM User s where s.role = :role", User.class)
                .setParameter("role", Role.USER)
                .getResultList();
    }
}
