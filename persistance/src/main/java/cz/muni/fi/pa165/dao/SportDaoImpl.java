package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Sport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Martin Šmíd
 */
@Repository
public class SportDaoImpl extends BaseDaoImpl<Sport> implements SportDao {

    @Override
    public Sport findById(Long id) {
        return findById(Sport.class, id);
    }

    @Override
    public List<Sport> findAll() {
        return entityManager.createQuery("SELECT s FROM Sport s", Sport.class).getResultList();
    }
}