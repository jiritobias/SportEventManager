package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.CompetitionDao;
import cz.fi.muni.pa165.dao.SportDao;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Petra Halová on 22.11.17.
 */
public class CompetitionServiceImpl implements CompetitionService {

    @Inject
    private CompetitionDao competitionDao;

    @Inject
    private SportDao sportDao;


    @Override
    public void create(Competition entity) {
        sportDao.create(entity.getSport());
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

    @Override
    public void addSportMen(Competition competition, User sportsMan) {
        if(!competition.getSportsMen().contains(sportsMan)){
            competition.addSportman(sportsMan);
        }
    }
}
