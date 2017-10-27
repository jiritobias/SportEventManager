package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author Martin Šmíd
 */
public class CompetitionDaoImplTest extends BaseDaoImplTest {

    @Autowired
    private CompetitionDao competitionDao;

    private Competition competition;

    @BeforeMethod
    public void setUp() {
        competition = new Competition();
        Sport sport = new Sport();
        sport.setName("Tennis");
        competition.setSport(sport);

        User sportman = new User();
        sportman.setFirstname("Martin");
        sportman.setLastname("Novy");
        sportman.setEmail("test@test.com");
        sportman.setAddress("address");
        sportman.setGendre(Gendre.MEN);
        sportman.setRole(Role.SPORTSMEN);

        User sportwoman = new User();
        sportwoman.setFirstname("Pavla");
        sportwoman.setLastname("Stara");
        sportwoman.setEmail("abc@test.com");
        sportwoman.setAddress("place");
        sportwoman.setGendre(Gendre.WOMAN);
        sportwoman.setRole(Role.SPORTSMEN);

        competition.addSportman(sportman);
        competition.addSportman(sportwoman);
    }

    /**
     * Checks that will return competition with the ID
     */
    @Test
    public void testFindById() {
        competitionDao.create(competition);
        Competition comp = competitionDao.findById(competition.getId());

        assertEquals(competition.getId(), comp.getId());
        assertEquals("Tennis", comp.getSport().getName());
        assertEquals(2, comp.getSportsMen().size());
    }

    /**
     * Checks that will return all competition objects in DB
     */
    @Test
    public void testFindAll() {
        Competition competition2 = new Competition();
        Sport sport = new Sport();
        sport.setName("Swimming");
        competition2.setSport(sport);

        competitionDao.create(competition);
        competitionDao.create(competition2);

        List<Competition> competitions = competitionDao.findAll();
        assertEquals(2, competitions.size());
        assertEquals("Tennis", competitions.get(0).getSport().getName());
        assertEquals("Swimming", competitions.get(1).getSport().getName());
    }

    /**
     * Checks that will create competition object in DB.
     */
    @Test
    public void testCreate() {
        competitionDao.create(competition);
        assertEquals(1, competitionDao.findAll().size());

        Competition competition2 = new Competition();
        Sport sport = new Sport();
        sport.setName("Swimming");
        competition2.setSport(sport);
        competitionDao.create(competition2);
        assertEquals(2, competitionDao.findAll().size());
    }

    /**
     * Checks that null DAO object will return null for non existent ID and also that delete operation works.
     */
    @Test
    public void testDelete() {
        competitionDao.create(competition);
        Assert.assertNotNull(competitionDao.findById(competition.getId()));

        competitionDao.delete(competition);
        Assert.assertNull(competitionDao.findById(competition.getId()));
    }
}