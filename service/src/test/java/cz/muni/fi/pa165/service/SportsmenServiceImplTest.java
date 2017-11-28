package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.CompetitionDao;
import cz.fi.muni.pa165.dao.SportsMenDao;
import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.hibernate.service.spi.ServiceException;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Patre Halová on 27.11.17.
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class SportsmenServiceImplTest extends AbstractTestNGSpringContextTests {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private CompetitionDao competitionDao;

    @Mock
    private SportsMenDao sportsMenDao;

    @Autowired
    @InjectMocks
    private SportsmenService sportsmenService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    @InjectMocks
    private CompetitionService competitionService;

    @Autowired
    private SportService sportService;

    private User testSportsman;
    private Competition competition;
    private Competition anotherCompetition;

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
        sportService.create(sport);
        competition.setSport(sport);

        anotherCompetition = new Competition();
        Sport anotherSport = new Sport();
        anotherSport.setName("Being cool");
        sportService.create(anotherSport);
        anotherCompetition.setSport(sport);
    }

    @Test
    public void testRegisterToCompetition(){
        SportsmenService sportsmenService = new SportsmenServiceImpl(sportsMenDao);

        sportsmenService.registerUser(testSportsman, testSportsman.getPasswordHash(), testSportsman.getEmail());
        verify(sportsMenDao).create(testSportsman);
        competitionService.create(competition);
        verify(competitionDao).create(competition);

        when(sportsMenDao.findById(testSportsman.getId())).thenReturn(testSportsman);
        sportsmenService.registerToCompetition(testSportsman,competition);
        verify(sportsMenDao).update(testSportsman);
    }

    @Test
    public void testRegisterToNonExistingCompetition(){
        sportsmenService.registerUser(testSportsman, testSportsman.getPasswordHash(), testSportsman.getEmail());
        expectedException.expect(IllegalArgumentException.class);
        sportsmenService.registerToCompetition(testSportsman, competition);
    }

    @Test
    public void testFindAllRegistered(){
        sportsmenService.registerUser(testSportsman, testSportsman.getPasswordHash(), testSportsman.getEmail());
        competitionService.create(competition);
        competitionService.create(anotherCompetition);

        sportsmenService.registerToCompetition(testSportsman,competition);
        List<Competition> c = sportsmenService.findAllRegisteredCompetitions(testSportsman);
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