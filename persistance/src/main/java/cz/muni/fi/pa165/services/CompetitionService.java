package cz.muni.fi.pa165.services;

import cz.muni.fi.pa165.entity.Competition;
import cz.muni.fi.pa165.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface CompetitionService {
    public void registerToCompetition(Competition competition, User user);
}
