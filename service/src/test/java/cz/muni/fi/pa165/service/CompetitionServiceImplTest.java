package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author jiritobias
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CompetitionServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    CompetitionService competitionService;

    @Autowired
    SportService sportService;

    @Autowired
    SportsmenService sportsmenService;

    private Sport sport;
    private User sportsMen;

    @BeforeClass
    public void setUp() {
        sport = new Sport();
        sport.setName("Basketball");

        sportsMen = new User();
        sportsMen.setFirstname("Martin");
        sportsMen.setLastname("Novy");
        sportsMen.setGendre(Gendre.MAN);
        sportsMen.setAddress("address");
        sportsMen.setPhone("777666555");

        sportsmenService.registerUser(sportsMen, "password", "abc@test.com");
        sportService.create(sport);
    }

    @Test
    public void createAndFound() {
        Competition competition = new Competition();
        competition.setSport(sport);

        competitionService.create(competition);

        Competition found = competitionService.findById(competition.getId());
        assertThat(found)
                .isEqualTo(competition);

        competitionService.delete(competition);
        sportService.delete(sport);
    }

    @Test
    public void testFindAll() {
        List<Competition> list = competitionService.findAll();
        Sport sport = new Sport();
        sport.setName("XXX");

        Competition competition = new Competition();
        competition.setSport(sport);
        competition.setDate(new Date());

        sportService.create(sport);
        competitionService.create(competition);

        list = competitionService.findAll();
        assertThat(list)
                .isNotEmpty()
                .contains(competition);

        competitionService.delete(competition);
        sportService.delete(sport);

    }

    @Test
    public void testDelete() {
        Sport sport = new Sport();
        sport.setName("Beer pong");
        Competition competition = new Competition();
        competition.setSport(sport);

        sportService.create(sport);
        competitionService.create(competition);

        List<Competition> list = competitionService.findAll();
        assertThat(list)
                .contains(competition);

        competitionService.delete(competition);

        list = competitionService.findAll();
        assertThat(list)
                .doesNotContain(competition);

        sportService.delete(sport);
    }

    @Test
    public void testAddSportMen() {
        Sport sport = new Sport();
        sport.setName("Sport1");

        Competition competition = new Competition();
        competition.setSport(sport);
        competition.setDate(new Date());

        sportService.create(sport);
        competitionService.create(competition);

        competitionService.addSportMen(competition, sportsMen);

        Competition withSportMen = competitionService.findById(competition.getId());

        assertThat(withSportMen.getSportsMen())
                .containsOnly(sportsMen);
    }

}