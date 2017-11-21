package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author jiritobias, Petra Halová
 */
public class UserDaoImplTest extends BaseDaoImplTest {

    @Qualifier("userDaoImpl")
    @Autowired
    private UserDao userDao;

    @Autowired
    private CompetitionDao competitionDao;

    @Autowired
    private SportDao sportDao;

    private User user1;
    private User user2;

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
    }

    @Test
    public void findByGendre() {
        Assertions.assertThat(userDao.findByGendre(Gendre.MAN))
                .usingFieldByFieldElementComparator()
                .containsOnly(user1);

        Assertions.assertThat(userDao.findByGendre(Gendre.WOMAN))
                .usingFieldByFieldElementComparator()
                .containsOnly(user2);
    }

    @Test
    public void findByEmail_existingEmail_shouldPass() {
        Assertions.assertThat(userDao.findByEmail("fake@gmail.com"))
                .isEqualToComparingFieldByField(user1);
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