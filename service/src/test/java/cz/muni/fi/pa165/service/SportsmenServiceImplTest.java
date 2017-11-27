package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.CompetitionDao;
import cz.fi.muni.pa165.dao.SportsMenDao;
import cz.fi.muni.pa165.dto.CompetitionDTO;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import org.assertj.core.api.Assertions;
import org.hibernate.service.spi.ServiceException;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Patre Halová on 27.11.17.
 */
public class SportsmenServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private CompetitionDao competitionDao;

    @Mock
    private SportsMenDao sportsMenDao;


    @Autowired
    @InjectMocks
    private SportsmenService sportsmenService;

    private User testSportsman;
    private Competition competition;
    private Competition anotherCompetition;


    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUpData() {
        testSportsman = new User();
        testSportsman.setAddress("Death Star 1");
        testSportsman.setEmail("darthVader@darkside.com");
        testSportsman.setFirstname("Darth");
        testSportsman.setLastname("Vader");
        testSportsman.setGendre(Gendre.MAN);
        testSportsman.setPasswordHash("666");
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.MARCH, 1, 1, 1, 1);
        Date date = cal.getTime();
        testSportsman.setBirthdate(date);

        competition = new Competition();
        Sport sport = new Sport();
        sport.setName("Fencing light swords");
        competition.setSport(sport);

        anotherCompetition = new Competition();
        Sport anotherSport = new Sport();
        sport.setName("Being cool");
        anotherCompetition.setSport(sport);
    }


    @Test
    public void testRegisterToCompetition(){
        sportsMenDao.create(testSportsman);
        competitionDao.create(competition);

        sportsmenService.registerToCompetition(testSportsman,competition);
    }

    @Test
    public void testRegisterToNonExistingCompetition(){
        sportsMenDao.create(testSportsman);
        expectedException.expect(IllegalArgumentException.class);
        sportsmenService.registerToCompetition(testSportsman, competition);
    }

    @Test
    public void testFindAllRegistered(){
        sportsMenDao.create(testSportsman);
        competitionDao.create(competition);
        competitionDao.create(anotherCompetition);

        sportsmenService.registerToCompetition(testSportsman,competition);
        Assertions.assertThat(sportsmenService.findAllRegisteredCompetitions(testSportsman))
                .usingFieldByFieldElementComparator()
                .containsOnly(competition);
    }

    @Test
    public void testUnregisterFromCompetition(){
        sportsMenDao.create(testSportsman);
        competitionDao.create(competition);
        competitionDao.create(anotherCompetition);

        sportsmenService.registerToCompetition(testSportsman,competition);
        sportsmenService.registerToCompetition(testSportsman,anotherCompetition);

        sportsmenService.unregisterFromCompetition(testSportsman,anotherCompetition);
        Assertions.assertThat(sportsmenService.findAllRegisteredCompetitions(testSportsman))
                .usingFieldByFieldElementComparator()
                .containsOnly(competition);
    }

    @Test
    public void testUnregisterFromNotRegisteredCompetition(){
        sportsMenDao.create(testSportsman);
        competitionDao.create(competition);

        expectedException.expect(IllegalArgumentException.class);
        sportsmenService.unregisterFromCompetition(testSportsman,competition);
    }

}