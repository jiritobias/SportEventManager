package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.AddSportsMenDTO;
import cz.fi.muni.pa165.dto.CompetitionDTO;
import cz.fi.muni.pa165.dto.CreateCompetitionDTO;
import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.facade.CompetitionFacade;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.CompetitionService;
import cz.muni.fi.pa165.service.SportService;
import cz.muni.fi.pa165.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Petra Halová on 22.11.17.
 */
@Service
@Transactional
public class CompetitionFacadeImpl implements CompetitionFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Inject
    private CompetitionService competitionService;

    @Inject
    private SportService sportService;

    @Named("sportsmenServiceImpl")
    @Inject
    private UserService sportsManService;

    @Override
    public void delete(CompetitionDTO dto) {
        competitionService.delete(competitionService.findById(dto.getId()));
    }

    @Override
    public CompetitionDTO load(Long id) {
        Competition competition = competitionService.findById(id);
        return beanMappingService.mapTo(competition, CompetitionDTO.class);
    }

    @Override
    public List<CompetitionDTO> getAll() {
        return beanMappingService.mapTo(competitionService.findAll(), CompetitionDTO.class);
    }

    @Override
    public Long createCompetition(CreateCompetitionDTO createCompetitionDTO) {
        Competition mappedCompetition = beanMappingService.mapTo(createCompetitionDTO, Competition.class);
        Sport sport = sportService.findById(createCompetitionDTO.getSport().getId());
        if (sport == null) {
            sport = new Sport();
            sport.setName(createCompetitionDTO.getSport().getName());
        }
        mappedCompetition.setSport(sport);

        competitionService.create(mappedCompetition);
        return mappedCompetition.getId();
    }

    @Override
    public void addSportsMen(AddSportsMenDTO addSportsManDTO) {
        competitionService.addSportMen(competitionService.findById(addSportsManDTO.getCompetition()), sportsManService.findById(addSportsManDTO.getSportsMan()));
    }

    @Override
    public List<SportsMenDTO> listAllRegisteredSportsMen(CompetitionDTO competitionDTO) {
        return beanMappingService.mapTo(competitionService.
                listAllRegisteredSportsMen(competitionService.findById(competitionDTO.getId())), SportsMenDTO.class);
    }
}
