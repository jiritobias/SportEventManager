package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Sport;

public interface BaseDao<T> {
    /**
     * Creates a record of Enitity object in DB
     * @param entity object to be created
     */
    void create(T entity);

    /**
     * Removes a Entity object from DB
     * @param entity object to be deleted
     */
    void delete(T entity);

}
