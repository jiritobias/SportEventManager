package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.CreateSportsMenDTO;
import cz.fi.muni.pa165.dto.SportsMenDTO;

/**
 * @author jiritobias
 */
public interface SportsMenFacade extends CrudFacade<SportsMenDTO> {

    /**
     *
     */
    Long createSportsMen(CreateSportsMenDTO createSportsMenDTO);
}
