package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.SportEvent;
import cz.fi.muni.pa165.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author jiritobias
 */
public class SportEventDaoImplTest extends BaseDaoImplTest {

    @Autowired
    SportEventDao sportEventDao;
    @Autowired
    SportDao sportDao;
    @Autowired
    SportsMenDao sportsMenDao;
    @Autowired
    CompetitionDao competitionDao;

    private SportEvent sportEvent1;
    private SportEvent sportEvent2;
    private Competition competition;

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

        Sport sport = new Sport();
        sport.setName("Ice hockey");
        sportDao.create(sport);

        User pepik = createSportsMen("Pepik");

        competition = new Competition();
        competition.setSport(sport);
        competition.addSportman(pepik);

        competitionDao.create(competition);

    }

    @Test
    public void findAll() {
        List<SportEvent> all = sportEventDao.findAll();

        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0).getName()).isEqualTo("OH");
        assertThat(all.get(1).getName()).isEqualTo("MS");

    }

    @Test
    public void delete() {
        sportEventDao.delete(sportEvent2);

        List<SportEvent> all = sportEventDao.findAll();
        assertThat(all.size()).isOne();
        assertThat(all.get(0).getName()).isEqualTo("OH");

        sportEventDao.delete(sportEvent1);
        all = sportEventDao.findAll();
        assertThat(all.size()).isZero();
    }

    @Test
    public void fail() {
        SportEvent sportEvent = new SportEvent();
        sportEvent.setName("OH");
        sportEvent.setPlace("Prague");
        // null parameters
        assertThatThrownBy(() -> sportEventDao.create(sportEvent))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void addAndRemoveCompetitonTest() {
        SportEvent sportEvent = new SportEvent();
        sportEvent.setName("ME");
        sportEvent.setPlace("Prague");
        sportEvent.setDate(new Date());

        sportEvent.addCompetition(competition);
        sportEventDao.create(sportEvent);

        SportEvent found = sportEventDao.findById(sportEvent.getId());

        assertThat(
                found
                        .getCompetitions()
                        .size())
                .isOne();

        assertThat(
                found
                        .getCompetitions()
                        .contains(competition))
                .isTrue();

        sportEvent.removeCompetition(competition);
        sportEventDao.update(sportEvent);

        SportEvent foundById = sportEventDao.findById(sportEvent.getId());
        assertThat(
                foundById
                        .getCompetitions()
                        .size())
                .isZero();
    }

}