package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.entity.BaseEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Martin Smid
 */
@Service
public interface BaseService<T extends BaseEntity> {

    /**
     * Creates the entity object.
     *
     * @param entity entity to be created
     * @throws DataAccessException in a case of failure on DAO layer
     */
    void create(T entity) throws DataAccessException;

    /**
     * Finds the entity object with the given ID.
     *
     * @param id id of the entity
     * @return entity object with the given ID
     * @throws DataAccessException in a case of failure on DAO layer
     */
    T findById(Long id) throws DataAccessException;

    /**
     * Finds all entity objects.
     *
     * @return list of entity objects
     * @throws DataAccessException in a case of failure on DAO layer
     */
    List<T> findAll() throws DataAccessException;

    /**
     * Deletes the entity object.
     *
     * @param entity entity object to be deleted
     * @throws DataAccessException in a case of failure on DAO layer
     */
    void delete(T entity) throws DataAccessException;
}
