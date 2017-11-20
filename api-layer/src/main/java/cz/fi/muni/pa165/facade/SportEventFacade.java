package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.CreateSportEventDTO;
import cz.fi.muni.pa165.dto.SportEventDTO;

/**
 * @author jiritobias
 */
public interface SportEventFacade extends BaseFacade<SportEventDTO> {

    /**
     * Find sportEvent by name;
     */
    Long findByName(String name);

    /**
     * Create new sportEvent.
     */
    Long createSportEvent(CreateSportEventDTO createSportEventDTO);
}
