package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Sport;

import java.util.List;

/**
 * @author Martin Šmíd
 */
public interface SportDao extends BaseDao<Sport>{
    /**
     * Finds a Sport object by its ID
     * @param id ID of the Sport object
     * @return Sport object with the input ID
     */
    Sport findById(Long id);

    /**
     * Finds all Sport objects in DB
     * @return a list of found Sport objects
     */
    List<Sport> findAllSports();
}