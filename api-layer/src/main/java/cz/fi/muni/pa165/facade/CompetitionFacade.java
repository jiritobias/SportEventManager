package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.CompetitionDTO;
import cz.fi.muni.pa165.dto.CreateCompetitionDTO;
import cz.fi.muni.pa165.dto.AddSportsMenDTO;

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
}
