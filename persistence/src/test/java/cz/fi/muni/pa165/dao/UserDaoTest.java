package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
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

        userDao.create(user);
    }

    @Test
    public void findAll() {
        List<User> allSportsMen = userDao.findAll();

        assertEquals(allSportsMen.size(), 1);
        assertEquals(allSportsMen.get(0).getRole(), Role.USER);
    }

}