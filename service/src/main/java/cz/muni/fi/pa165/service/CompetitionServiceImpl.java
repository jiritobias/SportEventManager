package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.CompetitionDao;
import cz.fi.muni.pa165.dao.SportDao;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Petra Halová on 22.11.17.
 */
@Service
@Transactional
public class CompetitionServiceImpl implements CompetitionService {

    @Inject
    private CompetitionDao competitionDao;

    @Inject
    private SportDao sportDao;


    @Override
    public void create(Competition entity) {
        competitionDao.create(entity);
    }

    @Override
    public Competition findById(Long id) {
        return competitionDao.findById(id);
    }

    @Override
    public List<Competition> findAll() {
        return competitionDao.findAll();
    }

    @Override
    public void delete(Competition entity) {
        competitionDao.delete(entity);
    }

    public void update(Competition entity){
        competitionDao.update(entity);
    }

    @Override
    public void addSportMen(Competition competition, User sportsMan) {
        if(!competition.getSportsMen().contains(sportsMan)){
            competition.addSportman(sportsMan);
            competitionDao.update(competition);
        }
    }

    @Override
    public List<User> listAllRegisteredSportsMen(Competition competition) {
        return new ArrayList<User>(competition.getSportsMen());
    }

    @Override
    public Set<Competition> listAllCompetitionsWithoutParticipants() {
        Set<Competition> emptyCompetitions = new HashSet<>();
        for(Competition competition: competitionDao.findAll()) {
            if (competition.getSportsMen().isEmpty()) {
                emptyCompetitions.add(competition);
            }
        }
        return emptyCompetitions;
    }

}

