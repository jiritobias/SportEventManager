package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.SportsMenDao;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Smid
 */
@Service
@Transactional
public class SportsmenServiceImpl extends UserServiceImpl implements SportsmenService {

    @Inject
    private SportsMenDao sportsMenDao;

    @Override
    public void registerToCompetition(User sportsman, Competition competition) {
        User foundUser = sportsMenDao.findById(sportsman.getId());
        if (foundUser != null) {
            sportsman.addToCompetition(competition);
            sportsMenDao.update(sportsman);
        } else {
            throw new IllegalArgumentException("Only registered sportsman can be added to the competition.");
        }
    }

    @Override
    public void unregisterFromCompetition(User sportsman, Competition competition) {
        User foundUser = sportsMenDao.findById(sportsman.getId());
        if (foundUser != null) {
            sportsman.removeFromCompetition(competition);
            sportsMenDao.update(sportsman);
        } else {
            throw new IllegalArgumentException("User is not registered.");
        }
    }

    @Override
    public List<Competition> findAllRegisteredCompetitions(User sportsman) {
        User foundUser = sportsMenDao.findById(sportsman.getId());
        return foundUser == null ? null : new ArrayList<>(foundUser.getCompetitions());
    }

    @Override
    public void registerUser(User user, String password, String email) {
        registerUserWithRole(user, password, email, sportsMenDao);
    }
}
