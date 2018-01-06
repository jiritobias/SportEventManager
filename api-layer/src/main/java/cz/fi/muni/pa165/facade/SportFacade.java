package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.SportDTO;

/**
 * @author Martin Smid
 */
public interface SportFacade extends BaseFacade<SportDTO> {

    /**
     * Create object by DTO params.
     *
     * @param sportDTO sport DTO
     * @return ID of the object
     */
    Long create(SportDTO sportDTO);

    /**
     * Update sport
     *
     * @param sportDTO sport DTO
     */
    void update(SportDTO sportDTO);
}
