package cz.fi.muni.pa165.dao;
import cz.fi.muni.pa165.entity.SportEvent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Petra Halová on 26.10.17.
 */
@Repository
public class SportEventDaoImpl extends BaseDaoImpl<SportEvent> implements SportEventDao {
    @Override
    public SportEvent findById(Long id) {
        return findByIdAndClass(SportEvent.class, id);
    }

    @Override
    public List<SportEvent> findAll() {
        return entityManager.createQuery("SELECT e FROM SportEvent e", SportEvent.class).getResultList();
    }
}
