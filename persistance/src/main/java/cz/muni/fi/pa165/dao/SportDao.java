package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Sport;

import java.util.List;

/**
 * @author Martin Šmíd
 */
public interface SportDao {

    /**
     * Creates a record of Sport object in DB
     * @param sport Sport object to be created
     */
    void create(Sport sport);

    /**
     * Removes a Sport object from DB
     * @param sport Sport object to be deleted
     */
    void delete(Sport sport);

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