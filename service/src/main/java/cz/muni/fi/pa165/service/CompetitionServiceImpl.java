package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.CompetitionDao;
import cz.fi.muni.pa165.entity.BaseEntity;
import cz.fi.muni.pa165.entity.Competition;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lenoch on 22.11.17.
 */
public class CompetitionServiceImpl implements CompetitionService {

    @Inject
    private CompetitionDao competitionDao;

    @Override
    public void create(Competition entity) {
        competitionDao.create(entity);
    }

    @Override
    public Competition findById(Long id) {
        return competitionDao.findById(id);
    }

    @Override
    public List findAll() {
      return competitionDao.findAll();
    }

    @Override
    public void delete(Competition entity) {
        competitionDao.delete(entity);
    }
}
