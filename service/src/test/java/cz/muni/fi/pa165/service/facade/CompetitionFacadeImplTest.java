package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.AddSportsMenDTO;
import cz.fi.muni.pa165.dto.CompetitionDTO;
import cz.fi.muni.pa165.dto.CreateCompetitionDTO;
import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.facade.CompetitionFacade;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CompetitionService;
import cz.muni.fi.pa165.service.SportService;
import cz.muni.fi.pa165.service.SportsmenService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Martin Smid
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CompetitionFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private BeanMappingService beanMappingService;
    @Mock
    private CompetitionService competitionService;
    @Mock
    private SportService sportService;
    @Mock
    private SportsmenService sportsmenService;

    @Autowired
    @InjectMocks
    private CompetitionFacade competitionFacade;

    private CompetitionDTO competitionDTO;
    private Competition competition;
    private SportDTO sportDTO;
    private Sport sport;

    @BeforeClass
    public void setUpClass() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUpData() {
        sportDTO = new SportDTO(1L, "Tennis");
        competitionDTO = new CompetitionDTO(1L, sportDTO, null);
        sport = new Sport();
        sport.setId(1L);
        sport.setName("Tennis");
        competition = new Competition();
        competition.setId(1L);
        competition.setSport(sport);
    }

    @Test
    public void testDelete() {
        // org.springframework.dao.InvalidDataAccessApiUsageException: attempt to create merge event with null entity; nested exception is java.lang.IllegalArgumentException: attempt to create merge event with null entity
        when(competitionService.findById(competitionDTO.getId())).thenReturn(competition);

        competitionFacade.delete(competitionDTO);

        verify(competitionService).delete(competition);
    }

    @Test
    public void testLoad() {
        when(competitionService.findById(1L)).thenReturn(competition);

        CompetitionDTO competitionDTO = competitionFacade.load(this.competitionDTO.getId());

        verify(competitionService).findById(1L);
    }

    @Test
    public void testGetAll() {
        List<Competition> competitions = Collections.singletonList(competition);
        List<CompetitionDTO> competitionDTOS = Collections.singletonList(competitionDTO);

        when(competitionService.findAll()).thenReturn(competitions);
        when(beanMappingService.mapTo(competitions, CompetitionDTO.class)).thenReturn(competitionDTOS);

        List<CompetitionDTO> dtos = competitionFacade.getAll();
        verify(competitionService).findAll();
        verify(beanMappingService).mapTo(competitions, CompetitionDTO.class);

        Assertions.assertThat(dtos).hasSize(1).contains(competitionDTO);
    }

    @Test
    public void testCreateCompetition() {
        CreateCompetitionDTO competitionDTO = new CreateCompetitionDTO(1L, sportDTO);

        when(sportService.findById(sportDTO.getId())).thenReturn(sport);
        when(beanMappingService.mapTo(competitionDTO, Competition.class)).thenReturn(competition);

        Long id = competitionFacade.createCompetition(competitionDTO);

        verify(competitionService).create(this.competition);
    }

    @Test
    public void testAddSportsMen() {
        User sportman = new User();
        sportman.setId(1L);
        sportman.setFirstname("Petr");
        sportman.setLastname("Novy");
        AddSportsMenDTO sportsMenDTO = new AddSportsMenDTO(sportman.getId(), competition.getId());

        when(competitionService.findById(competition.getId())).thenReturn(competition);
        when(sportsmenService.findById(sportsMenDTO.getSportsMan())).thenReturn(sportman);

        Assertions.assertThat(competition.getSportsMen()).isEmpty();

        competitionFacade.addSportsMen(sportsMenDTO);

        verify(competitionService).findById(competition.getId());
        verify(sportsmenService).findById(sportman.getId());
        verify(competitionService).addSportMen(competition, sportman);

        Assertions.assertThat(competition.getSportsMen()).hasSize(1).contains(sportman);
    }
}