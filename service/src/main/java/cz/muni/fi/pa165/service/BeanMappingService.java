package cz.muni.fi.pa165.service;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author Martin Smid
 */
@Service
public interface BeanMappingService {

    /**
     * Maps collection of objects to objects with destination class.
     *
     * @param objects    Collection of objects
     * @param matToClass destination class
     * @param <T>
     * @return mapped collection of objects
     */
    public <T> List<T> mapTo(Collection<?> objects, Class<T> matToClass);

    /**
     * Maps an object to destination class.
     *
     * @param obj        object to map
     * @param mapToClass destination class
     * @param <T>
     * @return mapped object
     */
    public <T> T mapTo(Object obj, Class<T> mapToClass);

    public Mapper getMapper();
}
