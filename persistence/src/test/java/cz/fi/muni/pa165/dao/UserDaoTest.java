package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * @author jiritobias
 */
public class UserDaoTest extends BaseDaoImplTest {

    @Qualifier("userDaoImpl")
    @Autowired
    private UserDao userDao;

    @BeforeMethod
    public void createUsers() {
        User user = new User();
        user.setAddress("addres");
        user.setEmail("fake@gmail.com");
        user.setFirstname("Honza");
        user.setLastname("Novotny");
        user.setGendre(Gendre.MAN);
        userDao.create(user);
    }

    @Test
    public void findAll() {
        List<User> allSportsMen = userDao.findAll();

        assertEquals(allSportsMen.size(), 1);
        assertEquals(allSportsMen.get(0).getRole(), Role.USER);
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

}