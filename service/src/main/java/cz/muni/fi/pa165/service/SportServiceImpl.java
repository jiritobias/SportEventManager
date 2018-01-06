package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.SportDao;
import cz.fi.muni.pa165.entity.Sport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Martin Smid
 */
@Service
@Transactional
public class SportServiceImpl implements SportService {

    @Inject
    private SportDao sportDao;

    @Override
    public void update(Sport sport) {
        sportDao.update(sport);
    }

    @Override
    public void create(Sport entity) {
        sportDao.create(entity);
    }

    @Override
    public Sport findById(Long id) {
        return sportDao.findById(id);
    }

    @Override
    public List<Sport> findAll() {
        return sportDao.findAll();
    }

    @Override
    public void delete(Sport entity) {
        sportDao.delete(entity);
    }

    @Override
    public Sport findByName(String name) {
        return sportDao.findByName(name);
    }
}
