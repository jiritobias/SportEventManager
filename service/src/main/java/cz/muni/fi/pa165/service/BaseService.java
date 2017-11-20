package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.entity.BaseEntity;
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
     */
    void create(T entity);

    /**
     * Finds the entity object with the given ID.
     *
     * @param id id of the entity
     * @return entity object with the given ID
     */
    T findById(Long id);

    /**
     * Finds all entity objects.
     *
     * @return list of entity objects
     */
    List<T> findAll();

    /**
     * Deletes the entity object.
     *
     * @param entity entity object to be deleted
     */
    void delete(T entity);
}
