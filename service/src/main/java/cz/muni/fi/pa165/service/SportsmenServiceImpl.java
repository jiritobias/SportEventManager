package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.SportsMenDao;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Smid
 */
@Service
public class SportsmenServiceImpl extends UserServiceImpl implements SportsmenService {

    @Inject
    private SportsMenDao sportsMenDao;

    @Override
    public void registerToCompetition(User user, Competition competition) {
        User foundUser = sportsMenDao.findById(user.getId());
        if (foundUser != null) {
            user.addToCompetition(competition);
        } else {
            throw new IllegalArgumentException("Only registered user can be added to the competition.");
        }
    }

    @Override
    public void unregisterFromCompetition(User user, Competition competition) {
        User foundUser = sportsMenDao.findById(user.getId());
        if (foundUser != null) {
            user.removeFromCompetition(competition);
        } else {
            throw new IllegalArgumentException("User is not registered.");
        }
    }

    @Override
    public List<Competition> findAllRegisteredCompetitions(User user) {
        User foundUser = sportsMenDao.findById(user.getId());
        return foundUser == null ? null : new ArrayList<>(foundUser.getCompetitions());
    }

    @Override
    public void registerUser(User user, String rawPassword) {
        registerUserWithRole(user, rawPassword, null, sportsMenDao);
    }

    @Override
    public void registerUser(User user, String rawPassword, String email) {
        registerUserWithRole(user, rawPassword, email, sportsMenDao);
    }
}
