package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.entity.Sport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Martin Smid
 */
@Service
@Transactional
public interface SportService extends BaseService<Sport> {

    /**
     * Finds a Sport object by name.
     *
     * @param name name of the object
     * @return found Sport object
     */
    Sport findByName(String name);
}
