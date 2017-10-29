package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * @author Martin Šmíd
 */
public class SportsMenDaoImplTest extends BaseDaoImplTest {

    @Autowired
    SportsMenDao sportsMenDao;

    @Autowired
    private CompetitionDao competitionDao;

    private User sportsmen;

    @BeforeMethod
    public void setUp() {
        sportsmen = new User();
        sportsmen.setRole(Role.SPORTSMEN);
        sportsmen.setFirstname("Martin");
        sportsmen.setLastname("Novy");
        sportsmen.setGendre(Gendre.MAN);
        sportsmen.setAddress("address");
        sportsmen.setEmail("abc@test.com");
        sportsmen.setPasswordHash("password");
        sportsmen.setPhone("777666555");
        sportsmen.setCompetitions(new HashSet<>());
    }

    @Test
    public void testCreate() {
        sportsMenDao.create(sportsmen);

        User found = sportsMenDao.findById(sportsmen.getId());
        assertNotNull(found);
        assertEquals(sportsMenDao.findAll().size(), 1);
        assertEquals(found, sportsmen);
    }

    @Test
    public void testAddToCompetition() {
        Competition competition = new Competition();
        competitionDao.create(competition);

        sportsMenDao.create(sportsmen);
        sportsMenDao.addToCompetition(competition, sportsmen);

        Competition foundCompetition = competitionDao.findById(competition.getId());
        Set<User> sportsmenInComp = foundCompetition.getSportsMen();

        assertTrue(sportsmen.getCompetitions().contains(competition));
        assertEquals(sportsmenInComp.size(), 1);
        assertTrue(sportsmenInComp.contains(sportsmen));
    }

    @Test
    public void testRemoveFromCompetition() {
        Competition competition = new Competition();
        competitionDao.create(competition);
        Competition competition1 = new Competition();
        competitionDao.create(competition1);

        sportsMenDao.create(sportsmen);

        sportsMenDao.removeFromCompetition(competition, sportsmen);
        assertEquals(sportsMenDao.findById(sportsmen.getId()).getCompetitions().size(), 0);

        sportsMenDao.addToCompetition(competition, sportsmen);
        sportsMenDao.addToCompetition(competition1, sportsmen);
        sportsMenDao.removeFromCompetition(competition, sportsmen);

        assertEquals(sportsmen.getCompetitions().size(), 1);
        Competition found = competitionDao.findById(competition1.getId());
        
        Set<User> sportsmenInComp = found.getSportsMen();

        assertTrue(sportsmen.getCompetitions().contains(competition1));
        assertEquals(sportsmenInComp.size(), 1);
        assertTrue(sportsmenInComp.contains(sportsmen));
    }

    @Test
    public void testFindAll() {
        sportsMenDao.create(sportsmen);

        List<User> found = sportsMenDao.findAll();
        assertEquals(found.size(), 1);
        assertEquals(found.get(0), sportsmen);
    }
}