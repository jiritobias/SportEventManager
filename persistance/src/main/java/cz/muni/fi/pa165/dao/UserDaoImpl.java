package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.Gendre;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public User findById(Long id) {
        return findById(User.class, id);
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
        return entityManager.createQuery("SELECT s FROM User s", User.class).getResultList();
    }
}
