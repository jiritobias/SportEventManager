package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.CompetitionDao;
import cz.fi.muni.pa165.dao.SportsMenDao;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Patre Halová on 27.11.17.
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class SportsmenServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CompetitionDao competitionDao;

    @Mock
    private SportsMenDao sportsMenDao;

    @InjectMocks
    private SportsmenService sportsmenService = new SportsmenServiceImpl();

    @InjectMocks
    private CompetitionService competitionService = new CompetitionServiceImpl();

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

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
        competition.setSport(sport);

        anotherCompetition = new Competition();
        Sport anotherSport = new Sport();
        anotherSport.setName("Being cool");
        anotherCompetition.setSport(anotherSport);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testCreate(){
        sportsmenService.create(testSportsman);
    }

    @Test
    public void testRegisterToCompetition(){
        when(sportsMenDao.findById(testSportsman.getId())).thenReturn(testSportsman);
        sportsmenService.registerToCompetition(testSportsman,competition);
        verify(sportsMenDao).update(testSportsman);

        Assertions.assertThat(competitionService.listAllRegisteredSportsMen(competition))
                .usingFieldByFieldElementComparator()
                .containsOnly(testSportsman);
    }

    @Test
    public void testFindAllRegistered(){
        when(sportsMenDao.findById(testSportsman.getId())).thenReturn(testSportsman);

        testSportsman.addToCompetition(competition);
        testSportsman.addToCompetition(anotherCompetition);

        Assertions.assertThat(sportsmenService.findAllRegisteredCompetitions(testSportsman))
                .usingFieldByFieldElementComparator()
                .containsOnly(competition, anotherCompetition);
    }

}