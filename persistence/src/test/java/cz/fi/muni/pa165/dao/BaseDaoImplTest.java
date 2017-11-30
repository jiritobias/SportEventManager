package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.AssertJUnit.assertTrue;

/**
 * @author jiritobias
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class BaseDaoImplTest extends AbstractTestNGSpringContextTests {

    @Qualifier("sportDaoImpl")
    @Autowired
    BaseDao<Sport> baseDao;

    @Qualifier("userDaoImpl")
    @Autowired
    BaseDao<User> userDao;

    @Qualifier("sportsMenDaoImpl")
    @Autowired
    BaseDao<User> sportsMenDao;

    @Test
    @Transactional
    public void createTest() {
        Sport sport = new Sport();
        sport.setName("First");

        baseDao.create(sport);

        List<Sport> all = baseDao.findAll();
        assertTrue(all.contains(sport));
    }

    @Test
    @Transactional
    public void updateTest(){
        User user = createUser("Karel");

        user.setLastname("Podivny");
        userDao.update(user);

        assertThat(user.getLastname())
                .isEqualTo("Podivny");

        userDao.delete(user);
    }

    protected User createUser(String firstname) {
        assert firstname != null;

        User user = buildUser(firstname);
        userDao.create(user);

        return user;
    }

    protected User buildUser(String firstname) {
        assert firstname != null;

        User user = new User();
        user.setPasswordHash("psswd");
        user.setFirstname(firstname);
        user.setLastname("Horolezec");
        user.setEmail(UUID.randomUUID().toString() + "@gmail.com");
        user.setGendre(Gendre.MAN);
        user.setAddress("Pricna ulice");

        return user;
    }

    protected User createSportsMen(String firstname) {
        assert firstname != null;

        User sportsMen = buildUser("SportsMen");
        sportsMenDao.create(sportsMen);

        return sportsMen;
    }
}