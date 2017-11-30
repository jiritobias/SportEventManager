package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.CompetitionDTO;
import cz.fi.muni.pa165.dto.CreateCompetitionDTO;
import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.facade.CompetitionFacade;
import cz.muni.fi.pa165.service.CompetitionService;
import cz.muni.fi.pa165.service.SportService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

/**
 * @author Martin Smid
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CompetitionFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CompetitionFacade competitionFacade;
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private SportService sportService;

    private CompetitionDTO competitionDTO;
    private Competition competition;
    private SportDTO sportDTO;
    private Sport sport;

    @BeforeClass
    public void setUpData() {

        sport = new Sport();
        sport.setName("Tennis");
        sportService.create(sport);

        competition = new Competition();
        competition.setSport(sport);

        competitionService.create(competition);

        sportDTO = new SportDTO(sport.getId(), "Tennis");
        competitionDTO = new CompetitionDTO(competition.getId(), sportDTO, null);
    }

    @AfterMethod
    public void tearDown() {
        try {
            sportService.delete(sport);
            competitionService.delete(competition);
        } catch (Exception ignore) { // ingore, delete tests
        }
    }

    @Test
    public void testGetAll() {
        Sport sport = new Sport();
        sport.setName("AAA");

        sportService.create(sport);

        Competition competition = new Competition();
        competition.setSport(sport);
        competitionService.create(competition);

        CompetitionDTO competitionDTO = new CompetitionDTO(competition.getId(), new SportDTO(sport.getId(), sport.getName()), null);

        List<CompetitionDTO> all = competitionFacade.getAll();

        Assertions.assertThat(all)
                .isNotEmpty()
                .contains(competitionDTO);
    }

    @Test
    public void loadAndDelete() {
        CompetitionDTO load = competitionFacade.load(competitionDTO.getId());

        Assertions.assertThat(competitionDTO)
                .isEqualTo(load);

        competitionFacade.delete(competitionDTO);
        // null object
        Assertions.assertThatThrownBy(() -> competitionFacade.load(competitionDTO.getId()))
                .isInstanceOf(MappingException.class);
    }

    @Test
    public void testCreateCompetition() {
        CreateCompetitionDTO createCompetitionDTO = new CreateCompetitionDTO(null, new SportDTO(sport.getId(), sport.getName()));
        Long competition = competitionFacade.createCompetition(createCompetitionDTO);
        Assertions.assertThat(competition)
                .isNotNull();
    }
}