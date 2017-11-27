package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author jiritobias
 */
public class CompetitionServiceImplTest extends BaseServiceTest {

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
    }

    @Test
    public void testFindAll() {
        List<Competition> list = competitionService.findAll();
        // precondition, empty db
        assertThat(list)
                .isEmpty();

        Competition competition = new Competition();
        competition.setSport(sport);

        competitionService.create(competition);

        list = competitionService.findAll();
        assertThat(list)
                .isNotEmpty()
                .containsOnly(competition);

    }

    @Test
    public void testDelete() {
        Competition competition = new Competition();
        competition.setSport(sport);

        competitionService.create(competition);
        List<Competition> list = competitionService.findAll();
        assertThat(list)
                .isNotEmpty()
                .containsOnly(competition);

        competitionService.delete(competition);

        list = competitionService.findAll();
        assertThat(list)
                .isEmpty();
    }

    @Test
    public void testAddSportMen() {
        Competition competition = new Competition();
        competition.setSport(sport);

        competitionService.create(competition);

        competitionService.addSportMen(competition, sportsMen);

        Competition withSportMen = competitionService.findById(competition.getId());

        assertThat(withSportMen.getSportsMen())
                .containsOnly(sportsMen);
    }

}