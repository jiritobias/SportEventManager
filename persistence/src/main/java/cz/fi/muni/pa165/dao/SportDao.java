package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Sport;

/**
 * @author Martin Šmíd
 */
public interface SportDao extends BaseDao<Sport> {

    /**
     * Find a sport entity object by its name.
     *
     * @param name name of the entity object
     * @return found Sport entity object
     */
    Sport findByName(String name);
}