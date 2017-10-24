package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Sport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Martin Šmíd
 */
@Repository
public class SportDaoImpl implements SportDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Sport sport) {
        em.persist(sport);
    }

    @Override
    public void delete(Sport sport) {
        em.remove(sport);
    }

    @Override
    public Sport findById(Long id) {
        return em.find(Sport.class, id);
    }

    @Override
    public List<Sport> findAllSports() {
        return em.createQuery("SELECT s FROM Sport s", Sport.class).getResultList();
    }
}