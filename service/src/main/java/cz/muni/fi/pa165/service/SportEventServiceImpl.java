package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.SportEventDao;
import cz.fi.muni.pa165.entity.SportEvent;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lenoch on 22.11.17.
 */
public class SportEventServiceImpl implements SportEventService {

    @Inject
    private SportEventDao sportEventDao;

    @Override
    public void create(SportEvent entity) {
        sportEventDao.create(entity);
    }

    @Override
    public SportEvent findById(Long id) {
        return sportEventDao.findById(id);
    }

    @Override
    public List<SportEvent> findAll() {
        return sportEventDao.findAll();
    }

    @Override
    public void delete(SportEvent entity) {
        sportEventDao.delete(entity);
    }
}
