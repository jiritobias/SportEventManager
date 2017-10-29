package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.BaseEntity;
import java.util.List;

/**
 * @author jiritobias
 */
public interface BaseDao<T extends BaseEntity> {
    /**
     * Creates a record of Enitity object in DB
     *
     * @param entity object to be created
     */
    void create(T entity);

    /**
     * Removes a Entity object from DB
     *
     * @param entity object to be deleted
     */
    void delete(T entity);

    /**
     * Finds a entity object by its ID
     *
     * @param id ID of the entity object
     * @return Entity object with the input ID
     */
    T findById(Long id);
    /**
     *
     * Updates entity.
     */
    void update(T entity);

    /**
     * Finds all entity objects in DB
     *
     * @return a list of found entity objects
     */
    List<T> findAll();

}
