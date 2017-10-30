package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Sport;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Petra Halová
 */
public class SportDaoImplTest extends BaseDaoImplTest {

    @Autowired
    private SportDao sportDao;

    private Sport sport1;
    private Sport sport2;

    @BeforeMethod
    public void setUp() {
        sport1 = new Sport();
        sport1.setName("Pole dance");

        sport2 = new Sport();
        sport2.setName("chess");

        sportDao.create(sport1);
        sportDao.create(sport2);
    }

    @Test
    public void testDeleteSport() {
        assertThat(sportDao.findById(sport1.getId())).isNotNull();
        assertThat(sportDao.findById(sport2.getId())).isNotNull();

        sportDao.delete(sport1);

        assertThat(sportDao.findById(sport2.getId())).isNotNull();
        assertThat(sportDao.findById(sport1.getId())).isNull();
    }

    @Test
    public void testFindAllSports(){
        assertThat(sportDao.findAll())
                .usingFieldByFieldElementComparator()
                .containsOnly(sport1,sport2);

        sportDao.delete(sport1);
        sportDao.delete(sport2);
        assertThat(sportDao.findAll()).isEmpty();
    }

    @Test
    public void testFindById(){
        Long id = sport1.getId();
        assertThat(sportDao.findById(id))
                .isEqualToComparingFieldByField(sport1);
    }
}