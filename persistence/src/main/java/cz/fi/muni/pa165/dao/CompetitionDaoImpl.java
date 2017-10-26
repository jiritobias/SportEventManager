package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenoch on 26.10.17.
 */
@Repository
public class CompetitionDaoImpl extends BaseDaoImpl<Competition> {

    @Override
    public Competition findById(Long id) {
        return findByIdAndClass(Competition.class, id);
    }

    @Override
    public List<Competition> findAll() {
        return entityManager.createQuery("SELECT c FROM Competition c", Competition.class).getResultList();
    }
}
