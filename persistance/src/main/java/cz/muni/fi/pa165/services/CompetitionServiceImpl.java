package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.dao.SportDao;
import cz.muni.fi.pa165.dao.SportsMenDao;
import cz.muni.fi.pa165.entity.Competition;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    SportsMenDao sportsMenDao;

    @Override
    public void registerToCompetition(Competition competition, User user) {
        assert competition != null;
        assert user != null;

        if(user.getRole() != Role.SPORTSMEN){
            throw  new UnsupportedOperationException("Must be sportsman");
        }
        sportsMenDao.addToCompetition(competition, user);
        // TODO add sportsmen to copmetition
    }
}
