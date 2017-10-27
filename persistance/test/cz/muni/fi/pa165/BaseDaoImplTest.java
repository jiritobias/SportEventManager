package java.cz.muni.fi.pa165;

import cz.muni.fi.pa165.ApplicationContext;
import cz.muni.fi.pa165.dao.BaseDao;
import cz.muni.fi.pa165.dao.SportsMenDao;
import cz.muni.fi.pa165.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ContextConfiguration(classes = ApplicationContext.class)
public class BaseDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    SportsMenDao sportsMenDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void test() {
        User user = new User();
    }
}