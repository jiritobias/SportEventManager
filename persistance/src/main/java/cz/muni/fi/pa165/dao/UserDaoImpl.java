package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Sport;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.Gendre;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        em.remove(entity);
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findByGendre(Gendre gendre) {
        assert gendre != null;

        return em
                .createQuery("SELECT s FROM User s where s.gendre = :gend", User.class)
                .setParameter("gend", gendre)
                .getResultList();
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT s FROM User s", User.class).getResultList();
    }
}
