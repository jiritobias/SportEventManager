package cz.fi.muni.pa165.facade;

import java.util.List;

/**
 * @author jiritobias
 */
public interface CrudFacade<T> {
    /**
     *
     */
    Long create(T dto);

    /**
     *
     */
    void update(T dto);

    /**
     *
     */
    void delete(T dto);

    /**
     *
     */
    T load(Long id);

    /**
     *
     */
    List<T> getAll();
}
