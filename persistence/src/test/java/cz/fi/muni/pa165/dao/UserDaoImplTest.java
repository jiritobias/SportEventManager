package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
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
    private User userWithCompetition;

    @BeforeMethod
    public void createUsers() {
        user1 = new User();
        user1.setAddress("addres");
        user1.setEmail("fake@gmail.com");
        user1.setFirstname("Honza");
        user1.setLastname("Novotny");
        user1.setGendre(Gendre.MAN);
        user1.setPasswordHash("1");
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2000, Calendar.MARCH, 1, 1, 1, 1);
        Date date1 = cal1.getTime();
        user1.setBirthdate(date1);
        user1.setRole(Role.USER);
        userDao.create(user1);

        user2 = new User();
        user2.setAddress("addres");
        user2.setEmail("fireandblood@gmail.com");
        user2.setFirstname("Daenerys");
        user2.setLastname("Targaryen");
        user2.setGendre(Gendre.WOMAN);
        user2.setPasswordHash("2");
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2000, Calendar.FEBRUARY, 1, 1, 1, 1);
        Date date2 = cal2.getTime();
        user2.setBirthdate(date2);
        user2.setRole(Role.USER);
        userDao.create(user2);
    }

    @AfterMethod
    public void tearDown()  {
        try {
            userDao.delete(user1);
            userDao.delete(user2);
        } catch (Exception ignore) {
        }
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
    public void findAllByRole() {
        Assertions.assertThat(userDao.findAll(Role.USER))
                .usingFieldByFieldElementComparator()
                .containsOnly(user1,user2);

        user2.setRole(Role.SPORTSMEN);
        userDao.update(user2);
        Assertions.assertThat(userDao.findAll(Role.SPORTSMEN)).containsOnly(user2);
        Assertions.assertThat(userDao.findAll(Role.USER)).containsOnly(user1);
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
    public void testDeleteUserWithCompetition() {
        Competition competition = createCompetition("Sport");
        user1.addToCompetition(competition);

        User byId = userDao.findById(user1.getId());
        Assertions.assertThat(byId).isNotNull();
        Set<User> sportsMen = competition.getSportsMen();
        Assertions.assertThat(sportsMen).contains(user1);

        userDao.delete(user1);

        Assertions.assertThat(userDao.findById(user1.getId())).isNull();
        Assertions.assertThat(competition.getSportsMen()).doesNotContain(user1);
    }

    @Test
    public void testAddToCompetition(){
        Competition competition = createCompetition("Flying on dragons");
        user2.addToCompetition(competition);

        Set<Competition> competitions = user2.getCompetitions();
        assertEquals(competitions.size(),1);
        assertTrue(competitions.contains(competition));

        sportDao.delete(competition.getSport());
        competitionDao.delete(competition);
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

        competitionDao.delete(competition1);
        competitionDao.delete(competition2);
    }

    @Test
    public void testFindByBirthdayWithinRange() {
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.JANUARY, 1, 1, 1, 1);
        Date date = cal.getTime();

        Assertions.assertThat(userDao.findByBirthdayWithinRange(date, date)).isEmpty();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(2000, Calendar.MARCH, 1, 1, 1, 1);
        Date date2 = cal2.getTime();

        Assertions.assertThat(userDao.findByBirthdayWithinRange(date2, date2)).contains(user1).hasSize(1);
        Assertions.assertThat(userDao.findByBirthdayWithinRange(date, date2)).contains(user2).hasSize(2);
    }
}