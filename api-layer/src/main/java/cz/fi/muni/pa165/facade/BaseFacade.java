package cz.fi.muni.pa165.facade;

import java.util.List;

/**
 * @author jiritobias
 */
public interface BaseFacade<T> {

    /**
     * Delete object by DTO param.
     */
    void delete(T dto);

    /**
     * Load object by DTO param;
     */
    T load(Long id);

    /**
     * Get all objects.
     */
    List<T> getAll();
}
