package cz.fi.muni.pa165.persistance;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.SportDao;
import cz.fi.muni.pa165.dao.SportEventDao;
import cz.fi.muni.pa165.entity.Sport;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import javax.validation.ConstraintViolationException;

/**
 * @author jiritobias
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ContextManagedEntityManagerTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    SportDao sportDao;

    @Autowired
    SportEventDao sportEventDao;

    @Test
    public void nameUnique() {
        Sport sport = new Sport();
        sport.setName("sport1");

        sportDao.create(sport);

        Sport sport1 = new Sport();
        sport1.setName("sport1");

        Assertions.assertThatThrownBy(() -> sportDao.create(sport1))
                .isInstanceOf(DataAccessException.class);

    }

    @Test
    public void nameNotNull() {
        Sport sport = new Sport();
        sport.setName(null);

        Assertions.assertThatThrownBy(() -> sportDao.create(sport))
                .isInstanceOf(ConstraintViolationException.class);
    }

}
