package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author jiritobias, Petra Halová
 */
public class UserDaoTest extends BaseDaoImplTest {

    @Qualifier("userDaoImpl")
    @Autowired
    private UserDao userDao;

    @Autowired
    private CompetitionDao competitionDao;

    @Qualifier("sportDaoImpl")
    @Autowired
    private SportDao sportDao;

    private User user1;
    private User user2;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeMethod
    public void createUsers() {
        user1 = new User();
        user1.setAddress("addres");
        user1.setEmail("fake@gmail.com");
        user1.setFirstname("Honza");
        user1.setLastname("Novotny");
        user1.setGendre(Gendre.MAN);
        user1.setPasswordHash("1");
        userDao.create(user1);

        user2 = new User();
        user2.setAddress("addres");
        user2.setEmail("fireandblood@gmail.com");
        user2.setFirstname("Daenerys");
        user2.setLastname("Targaryen");
        user2.setGendre(Gendre.WOMAN);
        user2.setPasswordHash("2");
        userDao.create(user2);
    }

    private Competition createCompetition(String nameOfSport){
        Sport sport = new Sport();
        sport.setName(nameOfSport);
        sportDao.create(sport);

        Competition competition = new Competition();
        competition.setSport(sport);
        competitionDao.create(competition);
        return competition;
    }

    @Test
    public void findAll() {
        Assertions.assertThat(userDao.findAll())
                .usingFieldByFieldElementComparator()
                .containsOnly(user1,user2);

/*
        List<User> allSportsMen = userDao.findAll();

        assertEquals(allSportsMen.size(), 1);
        assertEquals(allSportsMen.get(0).getRole(), Role.USER);
*/
    }

    @Test
    public void findByGendre() {
        List<User> womem = userDao.findByGendre(Gendre.WOMAN);
        List<User> men = userDao.findByGendre(Gendre.MAN);

        assertThat(womem.size() == 0);
        assertThat(men.size() == 1);
        assertThat(men.get(0).getFirstname())
                .isEqualTo("Honza");
    }

    @Test
    public void testCreateUser(){
        Long userId = user1.getId();
        Assert.assertNotNull(userId);

        Assertions.assertThat(userDao.findById(userId))
                .isEqualToComparingFieldByField(user1);
    }

    @Test
    public void testDeleteUser(){
        Assertions.assertThat(userDao.findById(user1.getId())).isNotNull();
        Assertions.assertThat(userDao.findById(user2.getId())).isNotNull();

        userDao.delete(user1);
        Assertions.assertThat(userDao.findById(user2.getId())).isNotNull();
        Assertions.assertThat(userDao.findById(user1.getId())).isNull();
    }

    @Test
    public void testDeleteWithNonExistingId(){
        user1.setId(11L);
        expectedException.expect(IllegalArgumentException.class);
        userDao.delete(user1);
    }

    @Test
    public void testFindByNonExistingId(){
        expectedException.expect(IllegalArgumentException.class);
        userDao.findById(11L);
    }

    @Test
    public void testAddToCompetition(){
        Competition competition = createCompetition("Flying on dragons");
        user2.addToCompetition(competition);

        Set<Competition> competitions = user2.getCompetitions();
        assertEquals(competitions.size(),1);
        assertTrue(competitions.contains(competition));
    }

    @Test
    public void testRemoveFromCompetition(){
        Competition competition1 = createCompetition("Flying on dragons");
        Competition competition2 = createCompetition("Burning");

        user2.addToCompetition(competition1);
        user2.addToCompetition(competition2);

        user2.removeFromCompetition(competition1);
        Set<Competition> competitions = user2.getCompetitions();
        assertEquals(competitions.size(),1);
        assertTrue(competitions.contains(competition2));
    }
}