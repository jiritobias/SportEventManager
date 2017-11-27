package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.CompetitionDTO;
import cz.fi.muni.pa165.dto.CreateCompetitionDTO;
import cz.fi.muni.pa165.dto.AddSportsMenDTO;
import cz.fi.muni.pa165.dto.SportsMenDTO;

import java.util.List;

/**
 * @author jiritobias
 */
public interface CompetitionFacade extends BaseFacade<CompetitionDTO> {
    /**
     * Create new competition.
     */
    Long createCompetition(CreateCompetitionDTO createCompetitionDTO);

    /**
     * Add sportsMan to competition.
     */
    void addSportsMen(AddSportsMenDTO addSportsManDTO);

    /**
     * Lists all participants in given competition
     */
    List<SportsMenDTO> listAllRegisteredSportsMen(CompetitionDTO competitionDTO);
}
