package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.entity.Sport;
import org.springframework.stereotype.Service;

/**
 * @author Martin Smid
 */
@Service
public interface SportService extends BaseService<Sport> {

    /**
     * Finds a Sport object by name.
     *
     * @param name name of the object
     * @return found Sport object
     */
    Sport findByName(String name);
}
