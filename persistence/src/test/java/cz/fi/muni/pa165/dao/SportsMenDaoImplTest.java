package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SportsMenDaoImplTest extends BaseDaoImplTest {
    @Autowired
    SportsMenDao sportsMenDao;

    @Test
    public void testCreate() {
        User user = new User();

        user.setAddress("Sport");
        user.setEmail("fake@gmail.com");
        user.setFirstname("Honza");
        user.setLastname("Novotny");

        sportsMenDao.create(user);
        List<User> all = sportsMenDao.findAll();
        assertThat(all.size() == 1);

    }
}