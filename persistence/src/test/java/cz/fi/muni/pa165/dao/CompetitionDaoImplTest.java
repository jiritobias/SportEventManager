package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
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
    @Autowired
    private SportsMenDao sportsMenDao;
    @Autowired
    private SportDao sportDao;

    private Competition competition;
    private Sport sport;
    private User sportman;
    private User sportwoman;

    @BeforeMethod
    public void setUp() {
        competition = new Competition();
        sport = createSport("Tennis");

        competition.setSport(sport);

        sportman = new User();
        sportman.setFirstname("Martin");
        sportman.setLastname("Novy");
        sportman.setEmail("test@test.com");
        sportman.setAddress("address");
        sportman.setGendre(Gendre.MAN);
        sportman.setRole(Role.SPORTSMEN);
        sportman.setPasswordHash("hash");

        sportwoman = new User();
        sportwoman.setFirstname("Pavla");
        sportwoman.setLastname("Stara");
        sportwoman.setEmail("abc@test.com");
        sportwoman.setAddress("place");
        sportwoman.setGendre(Gendre.WOMAN);
        sportwoman.setRole(Role.SPORTSMEN);
        sportwoman.setPasswordHash("password");

        sportsMenDao.create(sportman);
        sportsMenDao.create(sportwoman);

        competition.addSportman(sportman);
        competition.addSportman(sportwoman);
    }

    @AfterMethod
    public void tearDown() {
        sportsMenDao.delete(sportman);
        sportsMenDao.delete(sportwoman);
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

        competitionDao.delete(competition);
    }

    /**
     * Checks that will return all competition objects in DB
     */
    @Test
    public void testFindAll() {
        Competition competition2 = new Competition();
        Sport swimming = createSport("Swimming");
        competition2.setSport(swimming);

        competitionDao.create(competition);
        competitionDao.create(competition2);

        List<Competition> competitions = competitionDao.findAll();
        assertEquals(2, competitions.size());
        assertEquals("Tennis", competitions.get(0).getSport().getName());
        assertEquals("Swimming", competitions.get(1).getSport().getName());

        competitionDao.delete(competition);
        competitionDao.delete(competition2);
    }

    /**
     * Checks that will create competition object in DB.
     */
    @Test
    public void testCreate() {
        competitionDao.create(competition);
        assertEquals(1, competitionDao.findAll().size());

        Competition competition2 = new Competition();
        Sport sport = createSport("Swimming");
        competition2.setSport(sport);
        competitionDao.create(competition2);
        assertEquals(2, competitionDao.findAll().size());

        competitionDao.delete(competition);
        competitionDao.delete(competition2);
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

    private Sport createSport(String name) {
        Sport sport = new Sport();
        sport.setName(name);

        sportDao.create(sport);

        return sport;
    }
}