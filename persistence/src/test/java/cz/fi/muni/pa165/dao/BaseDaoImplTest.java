package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class BaseDaoImplTest extends AbstractTestNGSpringContextTests {

    @Qualifier("sportDaoImpl")
    @Autowired
    BaseDao<Sport> baseDao;

    @Test
    public void createTest() {
        Sport sport = new Sport();
        sport.setName("First");

        baseDao.create(sport);

        List<Sport> all = baseDao.findAll();
        assertThat(all.size() == 1);

        Sport first = all.get(0);
        assertEquals(first, sport);

        Long id = first.getId();

        Sport byId = baseDao.findById(id);
        assertEquals(first, byId);

    }
}