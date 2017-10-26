package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Sport;
import java.util.List;

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

    /**
     * Finds a entity object by its ID
     * @param id ID of the entity object
     * @return Entity object with the input ID
     */
    T findById(Long id);

    /**
     * Finds all entity objects in DB
     * @return a list of found entity objects
     */
    List<T> findAll();

}
