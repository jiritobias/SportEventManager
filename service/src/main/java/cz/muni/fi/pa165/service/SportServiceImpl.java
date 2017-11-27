package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.SportDao;
import cz.fi.muni.pa165.entity.Sport;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Martin Smid
 */
@Service
public class SportServiceImpl implements SportService {

    @Inject
    private SportDao sportDao;

    @Override
    public void create(Sport entity) throws DataAccessException {
        sportDao.create(entity);
    }

    @Override
    public Sport findById(Long id) throws DataAccessException {
        return sportDao.findById(id);
    }

    @Override
    public List<Sport> findAll() throws DataAccessException {
        return sportDao.findAll();
    }

    @Override
    public void delete(Sport entity) throws DataAccessException {
        sportDao.delete(entity);
    }

    @Override
    public Sport findByName(String name) {
        return sportDao.findByName(name);
    }
}
