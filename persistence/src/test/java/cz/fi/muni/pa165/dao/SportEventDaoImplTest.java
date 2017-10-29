package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.SportEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class SportEventDaoImplTest extends BaseDaoImplTest {

    @Autowired
    SportEventDao sportEventDao;

    private SportEvent sportEvent1;
    private SportEvent sportEvent2;

    @BeforeMethod
    public void createEvents() {
        sportEvent1 = new SportEvent();
        sportEvent1.setName("OH");
        sportEvent1.setPlace("Monako");
        sportEvent1.setDate(new Date());

        sportEvent2 = new SportEvent();
        sportEvent2.setName("MS");
        sportEvent2.setPlace("USA");
        sportEvent2.setDate(new Date());

        sportEventDao.create(sportEvent1);
        sportEventDao.create(sportEvent2);

    }

    @Test
    public void findAll() {
        List<SportEvent> all = sportEventDao.findAll();

        assertThat(all.size() == 2);
        assertThat(all.get(0).getName()).isEqualTo("OH");
        assertThat(all.get(1).getName()).isEqualTo("MS");

    }

    @Test
    public void delete() {
        sportEventDao.delete(sportEvent2);

        List<SportEvent> all = sportEventDao.findAll();
        assertThat(all.size() == 1);
        assertThat(all.get(0).getName()).isEqualTo("OH");

        sportEventDao.delete(sportEvent1);
        all = sportEventDao.findAll();
        assertThat(all.size() == 0);
    }

    @Test
    public void fail(){
        SportEvent sportEvent = new SportEvent();
        // hashcode returns NullPointerException
        assertThatThrownBy(() -> sportEventDao.create(sportEvent))
                .isInstanceOf(NullPointerException.class);
    }

}